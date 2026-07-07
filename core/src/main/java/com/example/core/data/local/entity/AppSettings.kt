package com.example.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    val darkModeEnabled: Boolean = false,
    val dailyLimitEnabled: Boolean = true,
    val dailySmsLimit: Int = 500,
    val resumeOnReboot: Boolean = true,
    val language: String = "en",
    val lastSyncTimestamp: Long = 0L
)