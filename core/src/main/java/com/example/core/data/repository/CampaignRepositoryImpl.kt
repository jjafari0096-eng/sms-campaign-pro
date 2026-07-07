package com.example.core.data.repository

import androidx.room.withTransaction
import com.example.core.data.local.database.AppDatabase
import com.example.core.data.local.dao.CampaignDao
import com.example.core.data.local.dao.ContactDao
import com.example.core.data.local.dao.MessageLogDao
import com.example.core.data.local.entity.Campaign
import com.example.core.data.local.entity.MessageLog
import com.example.core.domain.model.CampaignStatus
import com.example.core.domain.repository.CampaignRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CampaignRepositoryImpl @Inject constructor(
    private val campaignDao: CampaignDao,
    private val messageLogDao: MessageLogDao,
    private val contactDao: ContactDao,
    private val database: AppDatabase
) : CampaignRepository {

    override suspend fun createCampaign(campaign: Campaign, contactIds: List<Long>): Long {
        return database.withTransaction {
            val campaignId = campaignDao.insertCampaign(campaign)
            
            // Get all contacts
            val contacts = contactDao.getContactsByIds(contactIds)
            
            val logs = contacts.map { contact ->
                MessageLog(
                    campaignId = campaignId,
                    phoneNumber = contact.phone,
                    message = campaign.message,
                    isSent = false
                )
            }
            logs.forEach { messageLogDao.insertLog(it) }
            
            // Update campaign with pending count
            val updatedCampaign = campaign.copy(
                id = campaignId,
                totalContacts = contacts.size,
                pendingMessages = contacts.size
            )
            campaignDao.updateCampaign(updatedCampaign)
            
            campaignId
        }
    }

    override suspend fun updateCampaign(campaign: Campaign) {
        campaignDao.updateCampaign(campaign)
    }

    override suspend fun updateCampaignStatus(campaignId: Long, status: CampaignStatus) {
        campaignDao.updateCampaignStatus(campaignId, status)
    }

    override suspend fun pauseCampaign(campaignId: Long) {
        updateCampaignStatus(campaignId, CampaignStatus.PAUSED)
    }

    override suspend fun resumeCampaign(campaignId: Long) {
        updateCampaignStatus(campaignId, CampaignStatus.RUNNING)
    }

    override suspend fun stopCampaign(campaignId: Long) {
        updateCampaignStatus(campaignId, CampaignStatus.STOPPED)
    }

    override fun getAllCampaigns(): Flow<List<Campaign>> {
        return campaignDao.getAllCampaigns()
    }

    override fun getActiveCampaigns(): Flow<List<Campaign>> {
        return campaignDao.getActiveCampaigns(
            listOf(CampaignStatus.RUNNING, CampaignStatus.SCHEDULED)
        )
    }

    override suspend fun getCampaignById(campaignId: Long): Campaign? {
        return campaignDao.getCampaignById(campaignId)
    }

    override fun getTotalCampaigns(): Flow<Int> {
        return campaignDao.getTotalCampaigns()
    }

    override fun getActiveCampaignsCount(): Flow<Int> {
        return campaignDao.getActiveCampaignsCount()
    }

    override fun getTotalSentMessages(): Flow<Int> {
        return campaignDao.getTotalSentMessages().map { it ?: 0 }
    }

    override fun getTotalFailedMessages(): Flow<Int> {
        return campaignDao.getTotalFailedMessages().map { it ?: 0 }
    }

    override fun getTotalPendingMessages(): Flow<Int> {
        return campaignDao.getTotalPendingMessages().map { it ?: 0 }
    }

    override suspend fun getDueScheduledCampaigns(): List<Campaign> {
        return campaignDao.getDueScheduledCampaigns(System.currentTimeMillis())
    }

    override suspend fun resetDailyCounters() {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val activeCampaigns = campaignDao.getActiveCampaignsSync(
            listOf(CampaignStatus.RUNNING, CampaignStatus.SCHEDULED)
        )
        activeCampaigns.forEach { campaign ->
            campaignDao.resetDailySentCount(campaign.id, today)
        }
    }

    override fun getLogsByCampaign(campaignId: Long): Flow<List<MessageLog>> {
        return messageLogDao.getLogsForCampaign(campaignId)
    }

    override suspend fun deleteCampaign(campaignId: Long) {
        database.withTransaction {
            messageLogDao.deleteLogsForCampaign(campaignId)
            campaignDao.updateCampaignStatus(campaignId, CampaignStatus.COMPLETED)
        }
    }
}