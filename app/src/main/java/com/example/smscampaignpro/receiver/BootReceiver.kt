package com.example.smscampaignpro.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.smscampaignpro.worker.DailyResetWorker
import com.example.smscampaignpro.service.SmsSenderService
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED && context != null) {
            // Resume daily reset worker
            val dailyWorkRequest = PeriodicWorkRequestBuilder<DailyResetWorker>(
                24, TimeUnit.HOURS
            ).build()
            WorkManager.getInstance(context).enqueue(dailyWorkRequest)
            
            // Start SMS sender service to resume active campaigns
            Intent(context, SmsSenderService::class.java).also { serviceIntent ->
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    context.startForegroundService(serviceIntent)
                } else {
                    context.startService(serviceIntent)
                }
            }
        }
    }
}