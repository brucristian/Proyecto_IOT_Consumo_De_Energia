package com.example.appvoltaje.adapters.infrastructure.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import com.example.appvoltaje.domain.model.SensorData
import com.example.appvoltaje.ports.out.SensorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.UUID

class SensorRepositoryImpl : SensorRepository {

    @Suppress("DEPRECATION")
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    private var bluetoothSocket: BluetoothSocket? = null

    private val _sensorFlow = MutableSharedFlow<SensorData>(replay = 1)

    override fun observeSensorData(): Flow<SensorData> {
        return _sensorFlow.asSharedFlow()
    }

    @SuppressLint("MissingPermission")
    fun connectToEsp32(macAddress: String) {
        val device = bluetoothAdapter?.getRemoteDevice(macAddress) ?: return
        val sppUuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                bluetoothAdapter?.cancelDiscovery()
                bluetoothSocket = device.createRfcommSocketToServiceRecord(sppUuid)
                bluetoothSocket?.connect()

                val reader = BufferedReader(InputStreamReader(bluetoothSocket?.inputStream))
                while (true) {
                    val rawLine = reader.readLine() ?: break
                    val parsedData = parseFrame(rawLine)
                    if (parsedData != null) {
                        _sensorFlow.emit(parsedData)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                closeConnection()
            }
        }
    }

    private fun parseFrame(rawLine: String): SensorData? {
        return try {
            val parts = rawLine.trim().split(",")
            if (parts.size >= 3) {
                SensorData(
                    voltage = parts[0].toFloat(),
                    temperature = parts[1].toFloat(),
                    magneticField = parts[2].toFloat(),
                    timestamp = System.currentTimeMillis()
                )
            } else null
        } catch (e: Exception) {
            null
        }
    }

    fun closeConnection() {
        try {
            bluetoothSocket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        bluetoothSocket = null
    }
}