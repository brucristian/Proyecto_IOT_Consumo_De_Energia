package com.example.appvoltaje

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.appvoltaje.adapters.infrastructure.auth.AuthRepositoryImpl
import com.example.appvoltaje.adapters.infrastructure.bluetooth.BluetoothControllerImpl
import com.example.appvoltaje.adapters.infrastructure.bluetooth.SensorRepositoryImpl
import com.example.appvoltaje.adapters.ui.navigation.NavGraph
import com.example.appvoltaje.adapters.ui.theme.Theme
import com.example.appvoltaje.adapters.ui.viewmodel.AuthViewModel
import com.example.appvoltaje.adapters.ui.viewmodel.DashboardViewModel
import com.example.appvoltaje.adapters.ui.viewmodel.ScannerViewModel
import com.example.appvoltaje.domain.usecase.ObserveSensorDataCaseUse

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val bluetoothController = BluetoothControllerImpl(applicationContext)
        val sensorRepository = SensorRepositoryImpl()
        val authRepository = AuthRepositoryImpl()

        val observeSensorDataUseCase = ObserveSensorDataCaseUse(sensorRepository)


        val authViewModel = AuthViewModel(authRepository)
        val scannerViewModel = ScannerViewModel(bluetoothController)
        val dashboardViewModel = DashboardViewModel(observeSensorDataUseCase)

        setContent {
            Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    NavGraph(
                        authViewModel = authViewModel,
                        scannerViewModel = scannerViewModel,
                        dashboardViewModel = dashboardViewModel,
                        sensorRepository = sensorRepository
                    )
                }
            }
        }
    }
}