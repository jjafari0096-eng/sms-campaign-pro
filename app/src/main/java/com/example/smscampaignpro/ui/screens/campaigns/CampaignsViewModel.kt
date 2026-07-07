package com.example.smscampaignpro.ui.screens.campaigns

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.local.entity.Campaign
import com.example.core.domain.repository.CampaignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CampaignsViewModel @Inject constructor(
    campaignRepository: CampaignRepository
) : ViewModel() {
    val campaigns: StateFlow<List<Campaign>> = campaignRepository.getAllCampaigns()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}