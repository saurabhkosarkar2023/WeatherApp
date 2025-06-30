package com.example.skysync.dashBoard.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.skysync.shared.ui.additionalParameters
import com.example.skysync.shared.ui.getWeatherCondition
import com.example.skysync.shared.ui.threeDayForecast
import com.example.skysync.R
import com.example.skysync.shared.ui.extractHourlyTemperature
import com.example.skysync.shared.ui.extractHourlyTimePart
import com.example.skysync.shared.ui.extractWeatherCode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherDataViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.fetchWeatherData()
    }
    val scope = rememberCoroutineScope()
    val state = rememberPullToRefreshState()
    var refreshing by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp
    var weatherTemp by remember { mutableStateOf("") }
    var weatherCondition by remember { mutableStateOf("") }
    var sunriseTimimg by remember { mutableStateOf("") }
    var sunsetTiming by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }
    var hourlyTemp by remember { mutableStateOf<List<Double>>(emptyList()) }
    var hourlyTime by remember { mutableStateOf<List<String>>(emptyList()) }
    var hourlyWeather by remember { mutableStateOf<List<Int>>(emptyList()) }

    val weatherData by viewModel.weatherData.collectAsState()
    weatherTemp = weatherData.current.temperature2M.toString()
    weatherCondition = getWeatherCondition(weatherData.current.weatherCode)
    var isDay by remember { mutableStateOf(false) }
    LaunchedEffect(weatherData) {
        val sunriseList = weatherData.daily.sunrise
        val sunsetList = weatherData.daily.sunset
        val humidCheck = weatherData.current.relativeHumidity2M
        isDay=weatherData.current.isDay.toInt() == 0
        if (humidCheck != null) {
            humidity = humidCheck.toString()
        }
        if (sunriseList.isNotEmpty() && sunsetList.isNotEmpty()) {
            sunriseTimimg = sunriseList.first().split("T").getOrNull(1) ?: "NA"
            sunsetTiming = sunsetList.first().split("T").getOrNull(1) ?: "NA"
        }
        //24-hour
        if (weatherData.hourly.time.isNotEmpty()) {
            val data = extractHourlyTimePart(weatherData.hourly.time)
            hourlyTime = data
        }
        if (weatherData.hourly.temperature_2m.isNotEmpty()) {
            val data = extractHourlyTemperature(weatherData.hourly.temperature_2m)
            hourlyTemp = data
        }
        if (weatherData.hourly.weather_code.isNotEmpty()) {
            val data = extractWeatherCode(weatherData.hourly.weather_code)
            hourlyWeather = data
        }
    }

    Scaffold(
        topBar = {
            Log.d("WeatherScreen", "What is the day >> $isDay")
            CenterAlignedTopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    actionIconContentColor = Color.White,
                    containerColor = if (isDay) Color(0xFF122B5E) else Color(0xFF3D7EFF)
                ),
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Location")
                    }
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "options")
                    }
                },
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        if (isDay) listOf(
                            Color(0xFF122B5E),
                            Color(0xFF6F90E3)
                        ) else listOf(Color(0xFF3D7EFF), Color(0xFFBCCBEB))
                    )
                ),
            state = state,
            isRefreshing = refreshing,
            onRefresh = {
                scope.launch {
                    Log.d("PullToRefresh", "Is refreshing before >> $refreshing")
                    refreshing = true
                    Log.d("PullToRefresh", "Is refreshing before the fetch >> $refreshing")
                    try {
                        val data = viewModel.fetchWeatherData()
                    } catch (exception: Exception) {
                        Log.e("Error", "Exception which pull to refresh >> ${exception.message}")
                    } finally {
                        Log.d("pullToRefresh", "Refresh action completed. >> $refreshing")
                        scope.launch {
                            delay(1000)
                            if (weatherData != null) {
                                refreshing = false
                            }
                        }

                    }
                    Log.d("PullToRefresh", "Is refreshing after the fetch >> $refreshing")
                }
            },
            indicator = {
                PullToRefreshDefaults.Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    state = state,
                    isRefreshing = refreshing
                )
            }
//            indicator = {
//                Indicator(
//                    modifier = Modifier.align(Alignment.TopCenter),
//                    isRefreshing = refreshing,
//                    state = state
////                    containerColor = MaterialTheme.colorScheme.primaryContainer,
////                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//                )
//            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)

            ) {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize(),

                    ) {
                    item() {
                        Spacer(modifier = Modifier.height(50.dp))
                    }
                    item {
                        Row() {
                            Text(
                                "$weatherTemp °",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontSize = 100.sp,
                                    fontWeight = FontWeight.W400,
                                    color = Color.White
                                )
                            )
                        }
                    }
                    item {
                        Text(
                            weatherCondition, style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.White
                            )
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        threeDayForecast(
                            forecastTime = hourlyTime,
                            forecastTemp = hourlyTemp,
                            forecastWeatherCode = hourlyWeather
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item() {
                        Row(
                        ) {
                            additionalParameters(
                                title = "Sunrise",
                                result = if (sunriseTimimg.isNotEmpty()) sunriseTimimg else "NA",
                                image = {
                                    Icon(
                                        painter = painterResource(R.drawable.sunrise),
                                        contentDescription = "Image",
                                        modifier = Modifier.size(70.dp, 70.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                            )
                            additionalParameters(
                                title = "Sunset",
                                result = sunsetTiming,
                                image = {
                                    Icon(
                                        painter = painterResource(R.drawable.sunset1),
                                        contentDescription = "Image",
                                        modifier = Modifier.size(70.dp, 70.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                            )
                        }
                    }
                    item() {
                        Row {
                            additionalParameters(
                                title = "Humidity",
                                result = humidity,
                                image = {
                                    Icon(
                                        painter = painterResource(R.drawable.humidity),
                                        contentDescription = "Image",
                                        modifier = Modifier.size(70.dp, 70.dp),
                                        tint = Color.Unspecified
                                    )
                                }
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}