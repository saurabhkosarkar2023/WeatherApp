package com.example.skysync.shared.ui

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable

@Composable
fun getWeatherCondition(code: Int) : String {
    return when (code) {
        0 -> "Clear sky"
        1 -> "Mainly clear"
        2 -> "Partly cloudy"
        3 -> "Cloudy"
        45 -> "Fog"
        48 -> "Rime fog"
        51 -> "Light drizzle"
        53 -> "Moderate drizzle"
        55 -> "Dense drizzle"
        56 -> "Light drizzle"
        57 -> "Dense drizzle"
        61 -> "Slight rain"
        63 -> "Moderate rain"
        65 -> "Heavy rain"
        66 -> "Light freezing rain"
        67 -> "Heavy freezing rain"
        71 -> "Slight snowfall"
        73 -> "Moderate snowfall"
        75 -> "Heavy snowfall"
        77 -> "Snow grains"
        80 -> "Slight rain showers"
        81 -> "Moderate rain showers"
        82 -> "Violent rain showers"
        85 -> "Slight snow shower"
        86 -> "Heavy snow shower"
        95 -> "Slight/Moderate Thunderstorm"
        96 -> "Slight or Moderate thunderstorm"
        99 -> "Slight(Heavy hail) thunderstorm"
        else -> "NA"
    }
}
