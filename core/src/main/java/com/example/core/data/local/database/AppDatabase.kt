package com.example.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core.data.local.dao.CampaignDao
import com.example.core.data.local.dao.ContactDao
import com.example.core.data.local.dao.MessageLogDao
import com.example.core.data.local.dao.MessageTemplateDao
import com.example.core.data.local.dao.NotificationDao
import com.example.core.data.local.dao.SettingsDao
import com.example.core.data.local.entity.AppNotification
import com.example.core.data.local.entity.AppSettings
import com.example.core.data.local.entity.Campaign
import com.example.core.data.local.entity.Contact
import com.example.core.data.local.entity.MessageLog
import com.example.core.data.local.entity.MessageTemplate
import com.example.core.data.util.Converters

@Database(
    entities = [
        Contact::class,
        Campaign::class,
        MessageLog::class,
        MessageTemplate::class,
        AppSettings::class,
        AppNotification::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun campaignDao(): CampaignDao
    abstract fun messageLogDao(): MessageLogDao
    abstract fun messageTemplateDao(): MessageTemplateDao
    abstract fun settingsDao(): SettingsDao
    abstract fun notificationDao(): NotificationDao
}