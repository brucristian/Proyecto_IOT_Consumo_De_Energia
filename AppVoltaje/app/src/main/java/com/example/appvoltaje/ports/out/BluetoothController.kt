package com.example.appvoltaje.ports.out

import com.example.appvoltaje.domain.model.EspDevice
import kotlinx.coroutines.flow.Flow

interface BluetoothController {
    val scannedDevices: Flow<List<EspDevice>>

    fun startDiscovery()

    fun stopDiscovery()

    fun connectToDevice(macAddress: String): Flow<String>

    fun disconnect()
}