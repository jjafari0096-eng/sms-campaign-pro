package com.example.smscampaignpro.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.core.domain.repository.CampaignRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyResetWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val campaignRepository: CampaignRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            campaignRepository.resetDailyCounters()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}