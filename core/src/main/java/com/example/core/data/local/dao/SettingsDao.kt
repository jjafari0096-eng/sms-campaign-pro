package com.example.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.local.entity.AppSettings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM app_settings WHERE id = 1")
    fun getAppSettings(): Flow<AppSettings>

    @Insert
    suspend fun insertSettings(settings: AppSettings)

    @Update
    suspend fun updateSettings(settings: AppSettings)

    @Query("UPDATE app_settings SET darkModeEnabled = :enabled WHERE id = 1")
    suspend fun updateDarkMode(enabled: Boolean)

    @Query("UPDATE app_settings SET dailyLimitEnabled = :enabled WHERE id = 1")
    suspend fun updateDailyLimit(enabled: Boolean)

    @Query("UPDATE app_settings SET resumeOnReboot = :enabled WHERE id = 1")
    suspend fun updateResumeOnReboot(enabled: Boolean)
}