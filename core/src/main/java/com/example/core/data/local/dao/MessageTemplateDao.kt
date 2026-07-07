package com.example.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.local.entity.MessageTemplate
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageTemplateDao {
    @Query("SELECT * FROM message_templates ORDER BY updatedAt DESC")
    fun getAllTemplates(): Flow<List<MessageTemplate>>

    @Insert
    suspend fun insertTemplate(template: MessageTemplate)

    @Update
    suspend fun updateTemplate(template: MessageTemplate)

    @Query("DELETE FROM message_templates WHERE id = :templateId")
    suspend fun deleteTemplate(templateId: Long)

    @Query("SELECT * FROM message_templates WHERE id = :templateId")
    suspend fun getTemplateById(templateId: Long): MessageTemplate?
}