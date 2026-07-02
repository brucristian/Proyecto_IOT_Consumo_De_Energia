package com.example.appvoltaje.adapters.ui.view

sealed class Screen(val route: String){
    object Splash : Screen("splash_screen")
    object Auth : Screen("auth_screen")
    object BluetoothConfig : Screen("bluetooth_config_screen")
    object SensorDetail : Screen("sensor_detail_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Scanner : Screen("scanner_screen")
    object Dashboard : Screen("dashboard_screen")
}