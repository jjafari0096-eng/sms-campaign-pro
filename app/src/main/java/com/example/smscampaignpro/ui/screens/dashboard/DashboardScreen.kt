package com.example.smscampaignpro.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.smscampaignpro.navigation.Screen
import com.example.smscampaignpro.ui.components.StatCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController, viewModel: DashboardViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") }
            )
        }
    ) { padding ->
        DashboardContent(padding, uiState, navController)
    }
}

@Composable
fun DashboardContent(
    padding: PaddingValues,
    uiState: DashboardUiState,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "Total Contacts",
                    value = uiState.totalContacts.toString(),
                    icon = Icons.Default.Person,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Screen.Contacts.route) }
                )
                StatCard(
                    title = "Active Campaigns",
                    value = uiState.activeCampaigns.toString(),
                    icon = Icons.Default.Send,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Screen.Campaigns.route) }
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard(
                    title = "SMS Sent Today",
                    value = uiState.smsSentToday.toString(),
                    icon = Icons.Default.TrendingUp,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Screen.Reports.route) }
                )
                StatCard(
                    title = "Pending Messages",
                    value = uiState.pendingMessages.toString(),
                    icon = Icons.Default.Send,
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(Screen.Scheduling.route) }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Quick Actions",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        item {
            Card(
                onClick = { navController.navigate(Screen.Campaigns.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Create New Campaign", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
        item {
            Card(
                onClick = { navController.navigate(Screen.Contacts.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Import Contacts from Excel", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

data class DashboardUiState(
    val totalContacts: Int = 0,
    val activeCampaigns: Int = 0,
    val smsSentToday: Int = 0,
    val pendingMessages: Int = 0
)