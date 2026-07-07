package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        passphrase: ByteArray
    ): AppDatabase {
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sms-campaign-db"
        )
            .openHelperFactory(factory)
            .addMigrations(com.example.core.data.local.database.MIGRATION_1_2)
            .addMigrations(com.example.core.data.local.database.MIGRATION_2_3)
            .build()
    }

    @Provides
    @Singleton
    fun provideKeyStoreManager(): com.example.core.security.KeyStoreManager {
        return com.example.core.security.KeyStoreManager()
    }

    @Provides
    fun provideDatabasePassphrase(
        @ApplicationContext context: Context,
        keyStoreManager: com.example.core.security.KeyStoreManager
    ): ByteArray {
        return keyStoreManager.getDatabasePassphrase(context)
    }

    @Provides
    fun provideContactDao(database: AppDatabase): com.example.core.data.local.dao.ContactDao {
        return database.contactDao()
    }

    @Provides
    fun provideCampaignDao(database: AppDatabase): com.example.core.data.local.dao.CampaignDao {
        return database.campaignDao()
    }

    @Provides
    fun provideMessageLogDao(database: AppDatabase): com.example.core.data.local.dao.MessageLogDao {
        return database.messageLogDao()
    }

    @Provides
    fun provideMessageTemplateDao(database: AppDatabase): com.example.core.data.local.dao.MessageTemplateDao {
        return database.messageTemplateDao()
    }

    @Provides
    fun provideSettingsDao(database: AppDatabase): com.example.core.data.local.dao.SettingsDao {
        return database.settingsDao()
    }

    @Provides
    fun provideNotificationDao(database: AppDatabase): com.example.core.data.local.dao.NotificationDao {
        return database.notificationDao()
    }

    @Provides
    @Singleton
    fun provideExcelParser(): com.example.core.data.util.ExcelParser {
        return com.example.core.data.util.ExcelParser()
    }
    
    @Provides
    @Singleton
    fun providePermissionManager(): com.example.core.util.PermissionManager {
        return com.example.core.util.PermissionManager()
    }
}