package com.example.smscampaignpro.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.core.data.local.dao.CampaignDao
import com.example.core.data.local.dao.MessageLogDao
import com.example.core.data.local.entity.MessageLog
import com.example.core.domain.model.CampaignStatus
import com.example.smscampaignpro.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsSenderService : Service() {
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    @Inject
    lateinit var campaignDao: CampaignDao

    @Inject
    lateinit var messageLogDao: MessageLogDao

    companion object {
        private const val TAG = "SmsSenderService"
        private const val NOTIFICATION_ID = 12345
        private const val CHANNEL_ID = "sms_sender_channel"
        const val EXTRA_CAMPAIGN_ID = "extra_campaign_id"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        serviceScope.launch {
            while (true) {
                val activeCampaigns = campaignDao.getActiveCampaignsSync(
                    listOf(CampaignStatus.RUNNING, CampaignStatus.SCHEDULED)
                )
                if (activeCampaigns.isEmpty()) {
                    stopSelf()
                    break
                }
                processPendingMessages(activeCampaigns)
                kotlinx.coroutines.delay(1000) // Rate limiting
            }
        }
        return START_NOT_STICKY
    }

    private suspend fun processPendingMessages(activeCampaigns: List<com.example.core.data.local.entity.Campaign>) {
        activeCampaigns.forEach { campaign ->
            val pendingLogs = messageLogDao.getLogsForCampaign(campaign.id).first()
                .filter { !it.isSent }
                .take(5) // Process 5 messages at a time

            pendingLogs.forEach { log ->
                val smsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (campaign.simSlot >= 0) {
                        SmsManager.getSmsManagerForSubscriptionId(campaign.simSlot)
                    } else {
                        SmsManager.getDefault()
                    }
                } else {
                    SmsManager.getDefault()
                }

                try {
                    smsManager.sendTextMessage(
                        log.phoneNumber,
                        null,
                        log.message,
                        null,
                        null
                    )
                    val updatedLog = log.copy(isSent = true)
                    messageLogDao.insertLog(updatedLog)

                    campaign.todaySentCount++
                    campaign.sentMessages++
                    campaign.pendingMessages--
                    campaignDao.updateCampaign(campaign)

                    if (campaign.todaySentCount >= campaign.dailyLimit) {
                        campaign.status = CampaignStatus.PAUSED
                        campaignDao.updateCampaign(campaign)
                    }

                    if (campaign.pendingMessages <= 0) {
                        campaign.status = CampaignStatus.COMPLETED
                        campaignDao.updateCampaign(campaign)
                    }
                } catch (e: Exception) {
                    val failedLog = log.copy(isSent = false, errorMessage = e.message)
                    messageLogDao.insertLog(failedLog)
                    campaign.failedMessages++
                    campaignDao.updateCampaign(campaign)
                    Log.e(TAG, "Failed to send SMS", e)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "SMS Sender Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Running SMS campaign in background"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("SMS Campaign Running")
        .setContentText("Sending messages in background")
        .setSmallIcon(R.drawable.ic_notification)
        .build()

    override fun onBind(intent: Intent?) = null

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    companion object {
        fun enqueueWork(context: Context, campaignId: Long) {
            val intent = Intent(context, SmsSenderService::class.java).apply {
                putExtra(EXTRA_CAMPAIGN_ID, campaignId)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
    }
}