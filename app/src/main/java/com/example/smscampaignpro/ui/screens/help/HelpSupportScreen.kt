package com.example.smscampaignpro.ui.screens.help

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HelpSupportScreen() {
    val faqs = listOf(
        "How to import contacts from Excel?" to "Use the import button in the Contacts screen and select your Excel file. Make sure your Excel has columns for phone number, first name, and last name.",
        "What's the daily SMS limit?" to "You can set your daily limit in Settings. The default is 100 messages per day.",
        "How to schedule a campaign?" to "When creating a new campaign, you can set a start time and date in the scheduling section.",
        "Can I use dual SIM cards?" to "Yes! You can select which SIM card to use for each campaign in the campaign creation screen.",
        "What file formats are supported for export?" to "You can export reports as Excel, CSV, or PDF files."
    )
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Help & Support") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Frequently Asked Questions",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                items(faqs.size) { index ->
                    val (question, answer) = faqs[index]
                    FaqCard(question = question, answer = answer)
                }
            }
        }
    }
}

@Composable
fun FaqCard(question: String, answer: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.titleMedium
            )
            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = answer,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}