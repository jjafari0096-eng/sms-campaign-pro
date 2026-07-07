package com.example.smscampaignpro.ui.screens.scheduling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.local.entity.CampaignStatus
import com.example.core.domain.repository.CampaignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SchedulingViewModel @Inject constructor(
    campaignRepository: CampaignRepository
) : ViewModel() {
    val scheduledCampaigns = campaignRepository.getCampaignsByStatus(listOf(CampaignStatus.SCHEDULED))
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}