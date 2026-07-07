package com.example.core.data.util

import androidx.room.TypeConverter
import com.example.core.domain.model.CampaignStatus
import java.util.Date

class Converters {
    @TypeConverter
    fun fromCampaignStatus(status: CampaignStatus): String {
        return status.name
    }

    @TypeConverter
    fun toCampaignStatus(status: String): CampaignStatus {
        return CampaignStatus.valueOf(status)
    }

    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(timestamp: Long): Date {
        return Date(timestamp)
    }
}