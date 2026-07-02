package com.example.appvoltaje.domain.usecase

import com.example.appvoltaje.domain.model.SensorData
import com.example.appvoltaje.ports.out.SensorRepository
import kotlinx.coroutines.flow.Flow


class ObserveSensorDataCaseUse(
    private val SensorRepository: SensorRepository
){
    operator fun invoke(): Flow<SensorData>{
        return SensorRepository.observeSensorData();
    }
}