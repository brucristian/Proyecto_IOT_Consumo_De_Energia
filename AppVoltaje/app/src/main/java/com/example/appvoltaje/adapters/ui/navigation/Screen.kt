package com.example.appvoltaje.adapters.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Scanner : Screen("scanner")
    object Dashboard : Screen("dashboard")
    object History : Screen("history")
    object Alerts : Screen("alerts")
    object Settings : Screen("settings")
}