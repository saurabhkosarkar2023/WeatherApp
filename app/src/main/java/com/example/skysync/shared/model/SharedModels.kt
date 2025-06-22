package com.example.skysync.shared.model

import kotlinx.serialization.*


/**Common(Shared) Model**/


@Serializable
data class WeatherData(
    val latitude: Double,
    val longitude: Double,
    @SerialName("generationtime_ms")
    val generationtimeMS: Double,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Long,
    val timezone: String,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    val elevation: Double,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    val current: Current,
    @SerialName("daily_units")
    val dailyUnits: DailyUnits,
    val daily: Daily,
)

@Serializable
data class Current (
    val time: String,
    val interval: Long,

    @SerialName("temperature_2m")
    val temperature2M: Double,

    @SerialName("weather_code")
    val weatherCode: Int,

    @SerialName("relative_humidity_2m")
    val relativeHumidity2M: Long,

    @SerialName("apparent_temperature")
    val apparentTemperature: Double,

    @SerialName("is_day")
    val isDay: Long,

    @SerialName("surface_pressure")
    val surfacePressure: Double,

    @SerialName("wind_speed_10m")
    val windSpeed10M: Double,

    @SerialName("wind_direction_10m")
    val windDirection10M: Long
)

@Serializable
data class CurrentUnits (
    val time: String,
    val interval: String,

    @SerialName("temperature_2m")
    val temperature2M: String,

    @SerialName("relative_humidity_2m")
    val relativeHumidity2M: String,

    @SerialName("apparent_temperature")
    val apparentTemperature: String,

    @SerialName("is_day")
    val isDay: String,

    @SerialName("surface_pressure")
    val surfacePressure: String,

    @SerialName("wind_speed_10m")
    val windSpeed10M: String,

    @SerialName("wind_direction_10m")
    val windDirection10M: String
)

@Serializable
data class Daily (
    val time: List<String>,
    val sunset: List<String>,
    val sunrise: List<String>,

    @SerialName("uv_index_max")
    val uvIndexMax: List<Double>
)

@Serializable
data class DailyUnits (
    val time: String,
    val sunset: String,
    val sunrise: String,

    @SerialName("uv_index_max")
    val uvIndexMax: String
)
