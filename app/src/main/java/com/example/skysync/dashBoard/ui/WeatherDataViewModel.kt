package com.example.skysync.dashBoard.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skysync.dashBoard.data.repository.WeatherRepo
import com.example.skysync.shared.model.Current
import com.example.skysync.shared.model.CurrentUnits
import com.example.skysync.shared.model.Daily
import com.example.skysync.shared.model.DailyUnits
import com.example.skysync.shared.model.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherDataViewModel @Inject constructor(
    val weatherRepo: WeatherRepo
) : ViewModel() {

    private var _isLoading = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    private var _weatherData = MutableStateFlow<WeatherData>(
        WeatherData(
            latitude = 0.0,
            longitude = 0.0,
            generationtimeMS = 0.0,
            utcOffsetSeconds = 0L,
            timezone = "",
            timezoneAbbreviation = "",
            elevation = 0.0,
            currentUnits = CurrentUnits(
                time = "",
                interval = "",
                temperature2M = "",
                relativeHumidity2M = "",
                apparentTemperature = "",
                isDay = "",
                surfacePressure = "",
                windSpeed10M = "",
                windDirection10M = ""
            ),
            current = Current(
                time = "",
                interval = 0L,
                temperature2M = 0.0,
                relativeHumidity2M = 0L,
                apparentTemperature = 0.0,
                isDay = 0L,
                surfacePressure = 0.0,
                windSpeed10M = 0.0,
                windDirection10M = 0L,
                weatherCode = 0,
            ),
            dailyUnits = DailyUnits(
                time = "",
                sunset = "",
                sunrise = "",
                uvIndexMax = ""
            ),
            daily = Daily(
                time = emptyList(),
                sunset = emptyList(),
                sunrise = emptyList(),
                uvIndexMax = emptyList()
            )
        )
    )
    var weatherData: StateFlow<WeatherData> = _weatherData

    fun fetchWeatherData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = weatherRepo.getWeatherData(lat = 12.971599, long = 77.594566)
                Log.d("Viewmodel-response", "What is the response >> ${response}")
                _weatherData.value = response
                _isLoading.value = false
            } catch (exception: Exception) {
                Log.d("Weather-viewmodel","What is the exception >> $exception")
                _isLoading.value = false
            }
        }
    }
}