package com.example.smscampaignpro.ui.screens.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.local.entity.MessageLog
import com.example.core.domain.repository.MessageLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    messageLogRepository: MessageLogRepository
) : ViewModel() {
    val messageLogs: StateFlow<List<MessageLog>> = messageLogRepository.getAllMessageLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}