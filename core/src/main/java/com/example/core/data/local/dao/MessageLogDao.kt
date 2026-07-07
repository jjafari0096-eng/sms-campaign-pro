package com.example.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.core.data.local.entity.MessageLog
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageLogDao {
    @Query("SELECT * FROM message_logs WHERE campaignId = :campaignId ORDER BY timestamp DESC")
    fun getLogsForCampaign(campaignId: Long): Flow<List<MessageLog>>

    @Insert
    suspend fun insertLog(log: MessageLog)

    @Query("DELETE FROM message_logs WHERE campaignId = :campaignId")
    suspend fun deleteLogsForCampaign(campaignId: Long)

    @Query("SELECT COUNT(*) FROM message_logs WHERE campaignId = :campaignId AND isSent = 1")
    fun getSentCountForCampaign(campaignId: Long): Flow<Int>
}