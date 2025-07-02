package com.example.skysync.shared.model

import kotlinx.serialization.*


/**Common(Shared) Model**/


@Serializable
data class WeatherData(
    val latitude: Double=0.0,
    val longitude: Double=0.0,
    @SerialName("generationtime_ms")
    val generationtimeMS: Double=0.0,
    @SerialName("utc_offset_seconds")
    val utcOffsetSeconds: Long,
    val timezone: String,
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    val elevation: Double,
    @SerialName("current_units")
    val currentUnits: CurrentUnits,
    val current: Current,
    @SerialName("hourly_units")
    val hourlyUnits: hourly_units,
    val hourly: hourly,
    @SerialName("daily_units")
    val dailyUnits: DailyUnits,
    val daily: Daily,
)

@Serializable
data class Current(
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
data class hourly(
    val time: List<String>,
    val temperature_2m : List<Double>,
    val weather_code : List<Int>,
)

@Serializable
data class CurrentUnits(
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
data class hourly_units(
    val time: String,

    @SerialName("temperature_2m")
    val temperature2M: String,

//    @SerialName("weather_code")
//    val weatherCode: Int,
)

@Serializable
data class Daily(
    val time: List<String>,
    val sunset: List<String>,
    val sunrise: List<String>,

    @SerialName("uv_index_max")
    val uvIndexMax: List<Double>
)

@Serializable
data class DailyUnits(
    val time: String,
    val sunset: String,
    val sunrise: String,

    @SerialName("uv_index_max")
    val uvIndexMax: String
)

val emptyWeatherData =  WeatherData(
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
    hourly = hourly(
        time = emptyList(),
        temperature_2m = emptyList(),
        weather_code = emptyList()
    ),
    hourlyUnits = hourly_units(
        time = "",
        temperature2M = "",
    ),
    daily = Daily(
        time = emptyList(),
        sunset = emptyList(),
        sunrise = emptyList(),
        uvIndexMax = emptyList()
    )
)
