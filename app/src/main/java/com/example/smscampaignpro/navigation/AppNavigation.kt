package com.example.smscampaignpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smscampaignpro.ui.screens.campaigns.CampaignsScreen
import com.example.smscampaignpro.ui.screens.contacts.ContactsScreen
import com.example.smscampaignpro.ui.screens.dashboard.DashboardScreen
import com.example.smscampaignpro.ui.screens.help.HelpScreen
import com.example.smscampaignpro.ui.screens.notifications.NotificationsScreen
import com.example.smscampaignpro.ui.screens.reports.ReportsScreen
import com.example.smscampaignpro.ui.screens.scheduling.SchedulingScreen
import com.example.smscampaignpro.ui.screens.settings.SettingsScreen
import com.example.smscampaignpro.ui.screens.templates.TemplatesScreen

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Campaigns : Screen("campaigns")
    object Contacts : Screen("contacts")
    object Templates : Screen("templates")
    object Scheduling : Screen("scheduling")
    object Reports : Screen("reports")
    object Settings : Screen("settings")
    object Notifications : Screen("notifications")
    object Help : Screen("help")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) { DashboardScreen(navController) }
        composable(Screen.Campaigns.route) { CampaignsScreen(navController) }
        composable(Screen.Contacts.route) { ContactsScreen(navController) }
        composable(Screen.Templates.route) { TemplatesScreen(navController) }
        composable(Screen.Scheduling.route) { SchedulingScreen(navController) }
        composable(Screen.Reports.route) { ReportsScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.Notifications.route) { NotificationsScreen(navController) }
        composable(Screen.Help.route) { HelpScreen(navController) }
    }
}