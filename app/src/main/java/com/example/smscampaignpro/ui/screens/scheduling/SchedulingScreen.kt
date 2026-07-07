package com.example.smscampaignpro.ui.screens.scheduling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.core.data.local.entity.Campaign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchedulingScreen(navController: NavHostController, viewModel: SchedulingViewModel = hiltViewModel()) {
    val scheduledCampaigns by viewModel.scheduledCampaigns.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scheduling Center") }
            )
        }
    ) { padding ->
        SchedulingContent(padding, scheduledCampaigns)
    }
}

@Composable
fun SchedulingContent(padding: PaddingValues, campaigns: List<Campaign>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Scheduled Campaigns",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(campaigns) { campaign ->
            ScheduledCampaignCard(campaign)
        }
    }
}

@Composable
fun ScheduledCampaignCard(campaign: Campaign) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = campaign.name,
                style = MaterialTheme.typography.titleLarge
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Scheduled for: ${campaign.scheduledStartTime}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Pending: ${campaign.totalRecipients - campaign.totalSent}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}