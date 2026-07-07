package com.example.smscampaignpro.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repository.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appSettingsRepository: AppSettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingsUiState())

    init {
        viewModelScope.launch {
            appSettingsRepository.getAppSettings().collect { settings ->
                _uiState.value = SettingsUiState(
                    darkModeEnabled = settings.darkModeEnabled,
                    dailyLimitEnabled = settings.dailyLimitEnabled,
                    resumeOnReboot = settings.resumeOnReboot
                )
            }
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.updateDarkMode(enabled)
        }
    }

    fun toggleDailyLimit(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.updateDailyLimit(enabled)
        }
    }

    fun toggleResumeOnReboot(enabled: Boolean) {
        viewModelScope.launch {
            appSettingsRepository.updateResumeOnReboot(enabled)
        }
    }
}