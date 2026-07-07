package com.example.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.core.domain.model.CampaignStatus

enum class ScheduleType {
    IMMEDIATE, ONCE, DAILY, WEEKLY, MONTHLY, RECURRING
}

@Entity(tableName = "campaigns")
data class Campaign(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val message: String,
    val status: com.example.core.domain.model.CampaignStatus = com.example.core.domain.model.CampaignStatus.DRAFT,
    val scheduleType: ScheduleType = ScheduleType.IMMEDIATE,
    val scheduledTime: Long? = null,
    val timeZone: String? = null,
    val dailyLimit: Int = 100,
    val simSlot: Int = 0, // 0 for SIM1, 1 for SIM2
    val totalContacts: Int = 0,
    val sentMessages: Int = 0,
    val failedMessages: Int = 0,
    val pendingMessages: Int = 0,
    val lastSentIndex: Int = 0,
    val todaySentCount: Int = 0,
    val lastResetDate: String? = null, // YYYY-MM-DD format
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)