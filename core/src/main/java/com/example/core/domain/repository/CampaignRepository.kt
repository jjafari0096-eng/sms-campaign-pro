package com.example.core.domain.repository

import com.example.core.data.local.entity.Campaign
import com.example.core.data.local.entity.CampaignStatus
import com.example.core.data.local.entity.MessageLog
import kotlinx.coroutines.flow.Flow

interface CampaignRepository {
    suspend fun createCampaign(campaign: Campaign, contacts: List<Long>): Long
    suspend fun updateCampaign(campaign: Campaign)
    suspend fun updateCampaignStatus(campaignId: Long, status: CampaignStatus)
    suspend fun pauseCampaign(campaignId: Long)
    suspend fun resumeCampaign(campaignId: Long)
    suspend fun stopCampaign(campaignId: Long)
    fun getAllCampaigns(): Flow<List<Campaign>>
    fun getActiveCampaigns(): Flow<List<Campaign>>
    suspend fun getCampaignById(campaignId: Long): Campaign?
    fun getTotalCampaigns(): Flow<Int>
    fun getActiveCampaignsCount(): Flow<Int>
    fun getTotalSentMessages(): Flow<Int>
    fun getTotalFailedMessages(): Flow<Int>
    fun getTotalPendingMessages(): Flow<Int>
    suspend fun getDueScheduledCampaigns(): List<Campaign>
    suspend fun resetDailyCounters()
    fun getLogsByCampaign(campaignId: Long): Flow<List<MessageLog>>
    suspend fun deleteCampaign(campaignId: Long)
}