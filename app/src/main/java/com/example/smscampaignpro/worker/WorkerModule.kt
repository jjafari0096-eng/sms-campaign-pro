package com.example.smscampaignpro.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkerModule @Inject constructor(
    private val context: Context
) {
    fun scheduleDailyResetWorker() {
        val dailyResetRequest = PeriodicWorkRequestBuilder<DailyResetWorker>(
            24, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "DailyResetWorker",
            ExistingPeriodicWorkPolicy.UPDATE,
            dailyResetRequest
        )
    }
}