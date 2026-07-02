package com.example.appvoltaje.adapters.infrastructure.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import com.example.appvoltaje.domain.model.EspDevice
import com.example.appvoltaje.ports.out.BluetoothController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission") // Ya pedimos los permisos en el Manifest
class BluetoothControllerImpl(
    private val context: Context
) : BluetoothController {

    private val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    private var bluetoothSocket: BluetoothSocket? = null

    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    // Flujo para emitir los dispositivos encontrados
    private val _scannedDevices = MutableStateFlow<List<EspDevice>>(emptyList())
    override val scannedDevices: Flow<List<EspDevice>>
        get() = _scannedDevices

    override fun startDiscovery(){
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscovery() {
        bluetoothAdapter?.cancelDiscovery()
    }

    override fun connectToDevice(macAddress: String): Flow<String> = flow {
        emit("Conectando...")
        val device = bluetoothAdapter?.getRemoteDevice(macAddress)

        if (device == null) {
            emit("Error: Dispositivo no encontrado")
            return@flow
        }

        try {
            // Creamos el socket de comunicación usando el UUID SPP
            bluetoothSocket = device.createRfcommSocketToServiceRecord(SPP_UUID)
            bluetoothAdapter?.cancelDiscovery()
            bluetoothSocket?.connect()

            emit("Conectado a ${device.name ?: macAddress}")


        } catch (e: IOException) {
            bluetoothSocket?.close()
            bluetoothSocket = null
            emit("Error de conexión: ${e.message}")
        }
    }.flowOn(Dispatchers.IO) // Ejecutamos esto en un hilo secundario para no congelar la UI

    override fun disconnect() {
        try {
            bluetoothSocket?.close()
            bluetoothSocket = null
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}