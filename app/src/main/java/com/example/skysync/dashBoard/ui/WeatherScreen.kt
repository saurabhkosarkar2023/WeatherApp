package com.example.skysync.dashBoard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.skysync.shared.ui.getWeatherCondition

@Composable
fun WeatherScreen(
    viewModel: WeatherDataViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchWeatherData()
    }
    var weatherTemp by remember { mutableStateOf("") }
    var weatherCondition by remember { mutableStateOf("") }
    val weatherData = viewModel.weatherData.collectAsState()
    weatherTemp = weatherData.value.current.temperature2M.toString()
    weatherCondition = getWeatherCondition(weatherData.value.current.weatherCode)
    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Brush.verticalGradient(listOf(Color(0xFFBCCBEB), Color(0xFF3D7EFF))))
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))
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
                Text(weatherCondition)
                Spacer(modifier = Modifier.weight(3f))
            }
        }
    }
}