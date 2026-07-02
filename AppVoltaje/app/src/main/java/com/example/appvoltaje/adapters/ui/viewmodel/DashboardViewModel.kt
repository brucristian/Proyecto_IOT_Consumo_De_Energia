package com.example.appvoltaje.adapters.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appvoltaje.domain.model.SensorData
import com.example.appvoltaje.domain.usecase.ObserveSensorDataCaseUse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val observeSensorDataUseCase: ObserveSensorDataCaseUse
) : ViewModel() {

    private val _sensorState = MutableStateFlow<SensorData?>(null)

    val sensorState: StateFlow<SensorData?> = _sensorState.asStateFlow()

    init {
        startObservingSensors()
    }

    private fun startObservingSensors() {
        viewModelScope.launch {
            observeSensorDataUseCase().collect { newData ->
                // Actualizamos el estado con el nuevo dato de voltaje/temperatura
                _sensorState.value = newData
            }
        }
    }
}