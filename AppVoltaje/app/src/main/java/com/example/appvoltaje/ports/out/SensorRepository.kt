package com.example.appvoltaje.ports.out
import com.example.appvoltaje.domain.model.SensorData
import kotlinx.coroutines.flow.Flow

interface  SensorRepository {
    fun observeSensorData(): Flow<SensorData>

}