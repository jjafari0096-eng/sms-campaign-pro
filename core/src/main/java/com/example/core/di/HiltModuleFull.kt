package com.example.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.core.data.local.AppDatabase
import com.example.core.data.local.dao.CampaignDao
import com.example.core.data.local.dao.ContactDao
import com.example.core.data.local.dao.ContactGroupDao
import com.example.core.data.local.dao.MessageLogDao
import com.example.core.data.local.dao.MessageTemplateDao
import com.example.core.data.repository.CampaignRepositoryImpl
import com.example.core.data.repository.ContactRepositoryImpl
import com.example.core.domain.repository.CampaignRepository
import com.example.core.domain.repository.ContactRepository
import com.example.core.utils.ExcelParser
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompleteHiltModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context, passphrase: ByteArray): AppDatabase {
        val supportFactory = SupportFactory(passphrase)
        
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .openHelperFactory(supportFactory)
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            })
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideDatabasePassphrase(): ByteArray {
        return SQLiteDatabase.getBytes("secure_encryption_key_2024".toCharArray())
    }

    @Provides
    @Singleton
    fun provideContactDao(database: AppDatabase): ContactDao = database.contactDao()

    @Provides
    @Singleton
    fun provideCampaignDao(database: AppDatabase): CampaignDao = database.campaignDao()

    @Provides
    @Singleton
    fun provideMessageLogDao(database: AppDatabase): MessageLogDao = database.messageLogDao()

    @Provides
    @Singleton
    fun provideContactGroupDao(database: AppDatabase): ContactGroupDao = database.contactGroupDao()

    @Provides
    @Singleton
    fun provideMessageTemplateDao(database: AppDatabase): MessageTemplateDao = database.messageTemplateDao()

    @Provides
    @Singleton
    fun provideContactRepository(impl: ContactRepositoryImpl): ContactRepository = impl

    @Provides
    @Singleton
    fun provideCampaignRepository(impl: CampaignRepositoryImpl): CampaignRepository = impl

    @Provides
    @Singleton
    fun provideExcelParser(): ExcelParser = ExcelParser()
}