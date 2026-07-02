package com.example.appvoltaje.adapters.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appvoltaje.adapters.infrastructure.bluetooth.SensorRepositoryImpl
import com.example.appvoltaje.adapters.ui.screen.*
import com.example.appvoltaje.adapters.ui.view.Screen
import com.example.appvoltaje.adapters.ui.viewmodel.AuthViewModel
import com.example.appvoltaje.adapters.ui.viewmodel.DashboardViewModel
import com.example.appvoltaje.adapters.ui.viewmodel.ScannerViewModel

@Composable
fun NavGraph(
    authViewModel: AuthViewModel,
    scannerViewModel: ScannerViewModel,
    dashboardViewModel: DashboardViewModel,
    sensorRepository: SensorRepositoryImpl
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route   // <-- empieza en Splash
    ) {

        // ── Splash ──────────────────────────────────────────────────────────
        composable(Screen.Splash.route) {
            SplashScreen(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToScanner = {
                    navController.navigate(Screen.Scanner.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Login ────────────────────────────────────────────────────────────
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onAuthSuccess = {
                    navController.navigate(Screen.Scanner.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Register ─────────────────────────────────────────────────────────
        composable(Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onAuthSuccess = {
                    navController.navigate(Screen.Scanner.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Scanner ──────────────────────────────────────────────────────────
        composable(Screen.Scanner.route) {
            ScannerScreen(
                viewModel = scannerViewModel,
                onDeviceSelected = { mac ->
                    sensorRepository.connectToEsp32(mac)
                    navController.navigate(Screen.Dashboard.route)
                }
            )
        }

        // ── Dashboard ─────────────────────────────────────────────────────────
        composable(Screen.Dashboard.route) {
            DashboardScreen(viewModel = dashboardViewModel)
        }
    }
}