package com.example.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.local.entity.Campaign
import com.example.core.data.local.entity.CampaignStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface CampaignDao {
    @Insert
    suspend fun insertCampaign(campaign: Campaign): Long

    @Update
    suspend fun updateCampaign(campaign: Campaign)

    @Query("UPDATE campaigns SET status = :status, updatedAt = :timestamp WHERE id = :campaignId")
    suspend fun updateCampaignStatus(campaignId: Long, status: CampaignStatus, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE campaigns SET sentMessages = :sent, failedMessages = :failed, pendingMessages = :pending, lastSentIndex = :lastIndex, todaySentCount = :todaySent, updatedAt = :timestamp WHERE id = :campaignId")
    suspend fun updateCampaignProgress(
        campaignId: Long,
        sent: Int,
        failed: Int,
        pending: Int,
        lastIndex: Int,
        todaySent: Int,
        timestamp: Long = System.currentTimeMillis()
    )

    @Query("UPDATE campaigns SET todaySentCount = 0, lastResetDate = :date WHERE id = :campaignId")
    suspend fun resetDailyCounter(campaignId: Long, date: String)

    @Query("SELECT * FROM campaigns ORDER BY createdAt DESC")
    fun getAllCampaigns(): Flow<List<Campaign>>

    @Query("SELECT * FROM campaigns WHERE status IN (:statuses)")
    fun getActiveCampaigns(statuses: List<CampaignStatus>): Flow<List<Campaign>>

    @Query("SELECT * FROM campaigns WHERE id = :campaignId")
    suspend fun getCampaignById(campaignId: Long): Campaign?

    @Query("SELECT COUNT(*) FROM campaigns")
    fun getTotalCampaigns(): Flow<Int>

    @Query("SELECT COUNT(*) FROM campaigns WHERE status = 'RUNNING'")
    fun getActiveCampaignsCount(): Flow<Int>

    @Query("SELECT SUM(sentMessages) FROM campaigns")
    fun getTotalSentMessages(): Flow<Int?>

    @Query("SELECT SUM(failedMessages) FROM campaigns")
    fun getTotalFailedMessages(): Flow<Int?>

    @Query("SELECT SUM(pendingMessages) FROM campaigns")
    fun getTotalPendingMessages(): Flow<Int?>

    @Query("SELECT * FROM campaigns WHERE status = 'SCHEDULED' AND scheduledTime <= :currentTime")
    suspend fun getDueScheduledCampaigns(currentTime: Long): List<Campaign>

    @Query("SELECT * FROM campaigns WHERE status IN (:statuses)")
    suspend fun getActiveCampaignsSync(statuses: List<CampaignStatus>): List<Campaign>

    @Query("UPDATE campaigns SET todaySentCount = 0, lastResetDate = :date WHERE id = :campaignId")
    suspend fun resetDailySentCount(campaignId: Long, date: String)
}