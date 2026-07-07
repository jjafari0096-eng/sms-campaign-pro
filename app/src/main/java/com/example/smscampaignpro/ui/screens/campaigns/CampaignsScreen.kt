package com.example.smscampaignpro.ui.screens.campaigns

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
fun CampaignsScreen(navController: NavHostController, viewModel: CampaignsViewModel = hiltViewModel()) {
    val campaigns by viewModel.campaigns.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Campaigns") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Open create campaign dialog */ }) {
                Icon(Icons.Default.Add, contentDescription = "Create campaign")
            }
        }
    ) { padding ->
        CampaignsContent(padding, campaigns)
    }
}

@Composable
fun CampaignsContent(padding: PaddingValues, campaigns: List<Campaign>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(campaigns) { campaign ->
            CampaignCard(campaign)
        }
    }
}

@Composable
fun CampaignCard(campaign: Campaign) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        androidx.compose.material3.CardDefaults
        androidx.compose.material3.CardColors
        androidx.compose.ui.Alignment
        androidx.compose.ui.layout.ContentScale
        androidx.compose.foundation.layout.Box
        androidx.compose.foundation.layout.Column
        androidx.compose.foundation.layout.fillMaxWidth
        androidx.compose.foundation.layout.padding
        androidx.compose.material3.Text
        androidx.compose.ui.Modifier
        androidx.compose.ui.unit.dp

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
                text = "Status: ${campaign.status.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Sent: ${campaign.totalSent} / ${campaign.totalRecipients}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}