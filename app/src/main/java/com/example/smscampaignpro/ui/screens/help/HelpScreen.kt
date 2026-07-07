package com.example.smscampaignpro.ui.screens.help

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support") }
            )
        }
    ) { padding ->
        HelpContent(padding)
    }
}

@Composable
fun HelpContent(padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(16.dp)
    ) {
        item {
            HelpSection(
                title = "Getting Started",
                content = "Learn how to import contacts, create campaigns, and send your first SMS blast."
            )
        }
        item {
            HelpSection(
                title = "Dual SIM Support",
                content = "Configure which SIM card to use for each campaign. This feature works on devices with dual SIM capabilities."
            )
        }
        item {
            HelpSection(
                title = "Daily Limits",
                content = "Set daily SMS limits to avoid carrier restrictions. The app automatically resets counters every 24 hours."
            )
        }
        item {
            HelpSection(
                title = "Excel Import/Export",
                content = "Import contacts from XLSX files. Export campaign reports to Excel, CSV, or PDF for analysis."
            )
        }
        item {
            HelpSection(
                title = "Contact Support",
                content = "Email: support@smscampaignpro.com\nWebsite: https://smscampaignpro.com"
            )
        }
    }
}

@Composable
fun HelpSection(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}