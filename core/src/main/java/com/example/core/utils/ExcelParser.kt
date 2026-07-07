package com.example.core.utils

import android.content.Context
import android.net.Uri
import com.example.core.data.local.entity.Contact
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.InputStream
import javax.inject.Inject

class ExcelParser @Inject constructor() {
    data class ImportResult(
        val validContacts: List<Contact>,
        val duplicates: List<Contact>,
        val invalidNumbers: List<Map<String, String>>,
        val totalProcessed: Int
    )

    suspend fun parseExcel(
        context: Context,
        uri: Uri,
        fieldMapping: Map<String, String>, // excel column -> our field
        existingPhones: Set<String>
    ): ImportResult {
        val validContacts = mutableListOf<Contact>()
        val duplicates = mutableListOf<Contact>()
        val invalidNumbers = mutableListOf<Map<String, String>>()
        
        var totalProcessed = 0

        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            processWorkbook(inputStream, fieldMapping, existingPhones, validContacts, duplicates, invalidNumbers)
            totalProcessed = validContacts.size + duplicates.size + invalidNumbers.size
        }

        return ImportResult(
            validContacts = validContacts,
            duplicates = duplicates,
            invalidNumbers = invalidNumbers,
            totalProcessed = totalProcessed
        )
    }

    private fun processWorkbook(
        inputStream: InputStream,
        fieldMapping: Map<String, String>,
        existingPhones: Set<String>,
        validContacts: MutableList<Contact>,
        duplicates: MutableList<Contact>,
        invalidNumbers: MutableList<Map<String, String>>
    ) {
        val workbook = WorkbookFactory.create(inputStream)
        val sheet = workbook.getSheetAt(0)
        val headerRow = sheet.getRow(0)
        
        val columnIndexMap = mutableMapOf<String, Int>()
        headerRow?.forEach { cell ->
            val columnName = cell.stringValue.trim()
            if (fieldMapping.containsKey(columnName)) {
                columnIndexMap[columnName] = cell.columnIndex
            }
        }

        for (i in 1..sheet.lastRowNum) {
            val row = sheet.getRow(i) ?: continue
            val contactData = mutableMapOf<String, String>()
            
            fieldMapping.forEach { (excelColumn, ourField) ->
                val columnIndex = columnIndexMap[excelColumn] ?: return@forEach
                val cell = row.getCell(columnIndex)
                val value = cell?.toString()?.trim() ?: ""
                contactData[ourField] = value
            }

            val phone = contactData["phone"] ?: ""
            if (isValidPhoneNumber(phone)) {
                val contact = Contact(
                    name = contactData["name"] ?: "",
                    phone = cleanPhoneNumber(phone),
                    company = contactData["company"],
                    city = contactData["city"]
                )

                if (existingPhones.contains(contact.phone)) {
                    duplicates.add(contact)
                } else {
                    validContacts.add(contact)
                }
            } else {
                invalidNumbers.add(contactData)
            }
        }
        workbook.close()
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        val cleaned = cleanPhoneNumber(phone)
        return cleaned.length >= 10 && cleaned.length <= 15
    }

    private fun cleanPhoneNumber(phone: String): String {
        return phone.replace(Regex("[^0-9+]"), "")
    }

    fun getExcelHeaders(context: Context, uri: Uri): List<String> {
        val headers = mutableListOf<String>()
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            val workbook = WorkbookFactory.create(inputStream)
            val sheet = workbook.getSheetAt(0)
            val headerRow = sheet.getRow(0)
            headerRow?.forEach { cell ->
                cell.stringValue.trim().takeIf { it.isNotEmpty() }?.let { headers.add(it) }
            }
            workbook.close()
        }
        return headers
    }
}