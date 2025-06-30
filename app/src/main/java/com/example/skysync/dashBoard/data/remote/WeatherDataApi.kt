package com.example.skysync.dashBoard.data.remote

import com.example.skysync.shared.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDataApi {
    @GET("forecast")
    suspend fun getWeatherData(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double,
        @Query("daily") daily : String = "sunset,sunrise,uv_index_max",
        @Query("hourly") hourly : Any = "temperature_2m,weather_code",
        @Query("current") current: String = "temperature_2m,relative_humidity_2m,weather_code,apparent_temperature,is_day,surface_pressure,wind_speed_10m,wind_direction_10m",
        @Query("timezone") timezone : String = "auto"
    ): WeatherData
}