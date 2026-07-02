package com.example.appvoltaje.adapters.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appvoltaje.ports.out.BluetoothController

class ScannerViewModel(
    private val bluetoothController: BluetoothController
) : ViewModel() {

    val scannedDevices = bluetoothController.scannedDevices

    fun startScanning() {
        bluetoothController.startDiscovery()
    }

    fun stopScanning() {
        bluetoothController.stopDiscovery()
    }
}