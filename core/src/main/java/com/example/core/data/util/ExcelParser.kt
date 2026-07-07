package com.example.core.data.util

import android.content.Context
import android.net.Uri
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream
import java.util.regex.Pattern

data class ExcelParseResult(
    val validNumbers: List<String>,
    val invalidNumbers: List<String>,
    val duplicates: List<String>,
    val totalRows: Int
)

class ExcelParser {
    private val phoneNumberPattern = Pattern.compile("^\\+?[1-9]\\d{1,14}$")

    fun parseContactsFromUri(context: Context, uri: Uri): ExcelParseResult {
        val inputStream = context.contentResolver.openInputStream(uri)
        val workbook: Workbook = XSSFWorkbook(inputStream)
        val sheet: Sheet = workbook.getSheetAt(0)
        
        val numbers = mutableListOf<String>()
        val invalidNumbers = mutableListOf<String>()
        val duplicates = mutableListOf<String>()
        val seen = mutableSetOf<String>()
        
        for (row in sheet) {
            if (row.rowNum == 0) continue // Skip header
            val cell = row.getCell(0) ?: continue
            val phoneNumber = cell.toString().trim()
            if (phoneNumberPattern.matcher(phoneNumber).matches()) {
                if (phoneNumber in seen) {
                    duplicates.add(phoneNumber)
                } else {
                    seen.add(phoneNumber)
                    numbers.add(phoneNumber)
                }
            } else {
                invalidNumbers.add(phoneNumber)
            }
        }
        
        workbook.close()
        return ExcelParseResult(
            validNumbers = numbers,
            invalidNumbers = invalidNumbers,
            duplicates = duplicates,
            totalRows = sheet.physicalNumberOfRows - 1
        )
    }

    fun exportCampaignReport(context: Context, campaignId: Long, logs: List<com.example.core.data.local.entity.MessageLog>): Uri? {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Campaign Report")
        
        // Header
        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Phone Number")
        headerRow.createCell(1).setCellValue("Message")
        headerRow.createCell(2).setCellValue("Timestamp")
        headerRow.createCell(3).setCellValue("Status")
        headerRow.createCell(4).setCellValue("Error")
        
        // Data rows
        logs.forEachIndexed { index, log ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(log.phoneNumber)
            row.createCell(1).setCellValue(log.message)
            row.createCell(2).setCellValue(java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date(log.timestamp)))
            row.createCell(3).setCellValue(if (log.isSent) "SENT" else "FAILED")
            row.createCell(4).setCellValue(log.errorMessage ?: "")
        }
        
        // Auto-size columns
        for (i in 0..4) {
            sheet.autoSizeColumn(i)
        }
        
        val fileName = "campaign_${campaignId}_report_${System.currentTimeMillis()}.xlsx"
        val file = java.io.File(context.filesDir, fileName)
        val outputStream = FileOutputStream(file)
        workbook.write(outputStream)
        workbook.close()
        outputStream.close()
        
        return Uri.fromFile(file)
    }
}