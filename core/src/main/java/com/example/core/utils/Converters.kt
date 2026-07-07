package com.example.core.utils

import androidx.room.TypeConverter
import com.example.core.data.local.entity.CampaignStatus
import com.example.core.data.local.entity.MessageStatus
import com.example.core.data.local.entity.ScheduleType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {
    private val gson = Gson()
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    @TypeConverter
    fun fromCampaignStatus(status: CampaignStatus): String {
        return status.name
    }

    @TypeConverter
    fun toCampaignStatus(value: String): CampaignStatus {
        return enumValueOf(value)
    }

    @TypeConverter
    fun fromScheduleType(type: ScheduleType): String {
        return type.name
    }

    @TypeConverter
    fun toScheduleType(value: String): ScheduleType {
        return enumValueOf(value)
    }

    @TypeConverter
    fun fromMessageStatus(status: MessageStatus): String {
        return status.name
    }

    @TypeConverter
    fun toMessageStatus(value: String): MessageStatus {
        return enumValueOf(value)
    }

    @TypeConverter
    fun fromStringMap(map: Map<String, String>?): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toStringMap(value: String): Map<String, String>? {
        val type = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, dateFormatter) }
    }

    @TypeConverter
    fun fromInstant(instant: Instant?): Long? {
        return instant?.toEpochMilli()
    }

    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) }
    }
}