package com.example.smscampaignpro.ui.screens.scheduling

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.data.local.entity.Campaign

@Composable
fun SchedulingCenterScreen(
    viewModel: SchedulingViewModel = hiltViewModel()
) {
    val scheduledCampaigns = viewModel.scheduledCampaigns.collectAsStateWithLifecycle(emptyList()).value
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scheduling Center") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(scheduledCampaigns) { campaign ->
                    ScheduledCampaignItem(campaign = campaign)
                }
            }
        }
    }
}

@Composable
fun ScheduledCampaignItem(campaign: Campaign) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = campaign.name,
                style = MaterialTheme.typography.titleMedium
            )
            campaign.scheduledStartTime?.let { startTime ->
                Text(
                    text = "Scheduled to start: $startTime",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "Recipients: ${campaign.totalRecipients}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}