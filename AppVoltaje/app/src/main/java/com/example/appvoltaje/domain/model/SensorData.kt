package com.example.appvoltaje.domain.model

data class SensorData(
    val voltage: Float,
    val temperature: Float,
    val magneticField: Float,
    val timestamp: Long,
)