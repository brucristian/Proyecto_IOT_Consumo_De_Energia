package com.example.appvoltaje.adapters.ui.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appvoltaje.adapters.ui.theme.PrimaryGreen
import com.example.appvoltaje.adapters.ui.viewmodel.ScannerViewModel

@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel,
    onDeviceSelected: (String) -> Unit
) {
    // Escucha el flujo reactivo de dispositivos Bluetooth detectados por la antena
    val devices by viewModel.scannedDevices.collectAsState(initial = emptyList())

    // Lanzador automático de solicitudes de permisos adaptado a las políticas de Android moderno
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            viewModel.startScanning()
        }
    }

    // EFECTO DE ARRANQUE: Al entrar a la pantalla, valida la versión de Android y pide permisos
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    // EFECTO DE MONTAJE: Si el usuario sale de esta pantalla, apaga el escáner para ahorrar batería
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopScanning()
        }
    }

    // INTERFAZ GRÁFICA
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Buscando tu ESP32...",
            style = MaterialTheme.typography.headlineMedium,
            color = PrimaryGreen
        )
        Text(
            text = "Asegúrate de que el Bluetooth del celular y la ESP32 estén encendidos.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Estado 1: La lista está vacía -> Mostramos progreso circular de búsqueda
        if (devices.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = PrimaryGreen)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Escaneando el entorno...",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            // Estado 2: Dispositivos encontrados -> Desplegamos la lista scrollable
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(devices) { device ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Al hacer clic, enviamos la dirección MAC física de vuelta al NavGraph
                                onDeviceSelected(device.macAdress)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = device.name ?: "Dispositivo sin nombre",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            supportingContent = {
                                Text(
                                    text = device.macAdress,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            trailingContent = {
                                Icon(
                                    imageVector = Icons.Default.Bluetooth,
                                    contentDescription = "Bluetooth Device",
                                    tint = PrimaryGreen
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}