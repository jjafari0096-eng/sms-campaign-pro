package com.example.smscampaignpro.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smscampaignpro.ui.screens.notifications.AppNotification

@Composable
fun NotificationItem(notification: AppNotification) {
    val cardBackground = if (notification.isRead) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.surface
    }
    
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium
                )
                val statusColor = when (notification.type) {
                    com.example.smscampaignpro.ui.screens.notifications.NotificationType.ERROR -> MaterialTheme.colorScheme.error
                    com.example.smscampaignpro.ui.screens.notifications.NotificationType.CAMPAIGN_COMPLETED -> MaterialTheme.colorScheme.primary
                    com.example.smscampaignpro.ui.screens.notifications.NotificationType.DAILY_LIMIT_REACHED -> MaterialTheme.colorScheme.tertiary
                    else -> MaterialTheme.colorScheme.secondary
                }
                androidx.compose.material3.Icon(
                    androidx.compose.material.icons.Icons.Default.Info,
                    contentDescription = null,
                    tint = statusColor
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = notification.message,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = notification.timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}