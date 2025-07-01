package com.example.skysync.dashBoard.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skysync.dashBoard.data.repository.WeatherRepo
import com.example.skysync.shared.model.WeatherData
import com.example.skysync.shared.model.emptyWeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDataViewModel @Inject constructor(
    val weatherRepo: WeatherRepo
) : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        _isLoading.value = false
        _weatherData.value = emptyWeatherData
        Log.d("WeatherDataViewModel", "ViewModel cleared")
    }

    private var _isLoading = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    private var _weatherData = MutableStateFlow<WeatherData>(emptyWeatherData)
    var weatherData: StateFlow<WeatherData> = _weatherData

    fun fetchWeatherData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = weatherRepo.getWeatherData(lat = 12.89, long = 77.63)
                Log.d("Viewmodel-response", "What is the response >> ${response}")
                _weatherData.value = response
                _isLoading.value = false
            } catch (exception: Exception) {
                Log.d("Weather-viewmodel", "What is the exception >> $exception")
                _isLoading.value = false
            }
        }
    }
}