package com.example.skysync.dashBoard.data.repository

import com.example.skysync.dashBoard.data.remote.WeatherDataApi
import com.example.skysync.shared.model.WeatherData
import javax.inject.Inject

class WeatherRepo @Inject constructor(
    private var weatherApi : WeatherDataApi
){
    suspend fun getWeatherData(lat : Double,long: Double) : WeatherData = weatherApi.getWeatherData(latitude = lat, longitude = long)
}