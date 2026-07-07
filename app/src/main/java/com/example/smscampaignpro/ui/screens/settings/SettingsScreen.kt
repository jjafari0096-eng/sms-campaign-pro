package com.example.smscampaignpro.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { padding ->
        SettingsContent(padding, uiState, viewModel)
    }
}

@Composable
fun SettingsContent(
    padding: PaddingValues,
    uiState: SettingsUiState,
    viewModel: SettingsViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SettingItem(
                title = "Dark Mode",
                isChecked = uiState.darkModeEnabled,
                onToggle = { viewModel.toggleDarkMode(it) }
            )
        }
        item {
            SettingItem(
                title = "Daily Limit Enabled",
                isChecked = uiState.dailyLimitEnabled,
                onToggle = { viewModel.toggleDailyLimit(it) }
            )
        }
        item {
            SettingItem(
                title = "Resume on Reboot",
                isChecked = uiState.resumeOnReboot,
                onToggle = { viewModel.toggleResumeOnReboot(it) }
            )
        }
    }
}

@Composable
fun SettingItem(
    title: String,
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Switch(checked = isChecked, onCheckedChange = onToggle)
        }
    }
}

data class SettingsUiState(
    val darkModeEnabled: Boolean = false,
    val dailyLimitEnabled: Boolean = true,
    val resumeOnReboot: Boolean = true
)