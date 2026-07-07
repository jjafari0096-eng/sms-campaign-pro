package com.example.smscampaignpro.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.local.entity.CampaignStatus
import com.example.core.data.local.entity.MessageLogStatus
import com.example.core.domain.repository.CampaignRepository
import com.example.core.domain.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val contactRepository: ContactRepository,
    private val campaignRepository: CampaignRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardUiState())

    init {
        viewModelScope.launch {
            combine(
                contactRepository.getTotalContacts(),
                campaignRepository.getCampaignsByStatus(listOf(CampaignStatus.RUNNING, CampaignStatus.PAUSED)),
                campaignRepository.getTodaySmsCount(),
                campaignRepository.getPendingMessageCount()
            ) { totalContacts, activeCampaigns, todaySms, pending ->
                DashboardUiState(
                    totalContacts = totalContacts,
                    activeCampaigns = activeCampaigns.size,
                    smsSentToday = todaySms,
                    pendingMessages = pending
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }
}