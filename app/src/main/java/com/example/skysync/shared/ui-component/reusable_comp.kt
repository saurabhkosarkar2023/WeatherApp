package com.example.skysync.shared.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.skysync.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun customTopAppBar() {
    CenterAlignedTopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            actionIconContentColor = Color.White,
            containerColor = Color(0xFF3D7EFF)
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

@Composable
fun getWeatherCondition(code: Int): String {
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

@Composable
fun threeDayForecast(
    forecastTime: List<String>,
    forecastWeatherCode: List<Int>,
    forecastTemp: List<Double>
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent.copy(alpha = 0.1f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            rows = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            userScrollEnabled = true
        ) {
            var finalTemp=listOf<Double>()
            var finalTime=listOf<String>()
            var finalWCode=listOf<Int>()
            if (forecastTemp.isNotEmpty() && forecastTime.isNotEmpty() && forecastWeatherCode.isNotEmpty()) {
              try {
                  val formatter = DateTimeFormatter.ofPattern("HH:00")
                  val currentTime = LocalTime.now().format(formatter).toString()
                  val currentIndex = forecastTime.indexOfFirst { predicate ->
                      predicate==currentTime                         //"0+-$currentTime:00"
                  }
                  finalTemp=forecastTemp.subList(currentIndex,currentIndex+24)
                  finalTime=forecastTime.subList(currentIndex,currentIndex+24)
                  finalWCode=forecastWeatherCode.subList(currentIndex,currentIndex+24)
              }
              catch (exception:Exception){
                  Log.d("Crash-error","Why Am I crashing bro >> ${exception.message} , ${exception.cause}")
                }
            }

            items(finalTime.size) { index ->
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        finalTemp[index].toString(),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                    weatherEmoji(finalWCode[index], finalTime[index])
                    Text(
                        if(index==0) "Now" else finalTime[index],
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun weatherEmoji(code: Int,time: String) {
    return when (code) {
        0 -> Icon(
            painter = if (time > "18:00" || time < "06:00") painterResource(R.drawable.night) else painterResource(R.drawable.clear_sky),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        1 -> Icon(
            painter = if (time > "18:00" && time < "05:00") painterResource(R.drawable.night) else painterResource(R.drawable.clear_sky),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        2 -> Icon(
            painter =if (time > "18:00" && time < "05:00") painterResource(R.drawable.night) else painterResource(R.drawable.partial_cloudy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        3 -> Icon(
            painter =if ("05:00" >= time || "18:00" < time) painterResource(R.drawable.night) else painterResource(R.drawable.cloudy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        45 -> Icon(
            painter = painterResource(R.drawable.foggy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        48 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        51 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        53 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        55 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        56 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        57 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        61 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        63 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        65 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        66 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        67 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        71 -> Icon(
            painter = painterResource(R.drawable.snowfall),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        73 -> Icon(
            painter = painterResource(R.drawable.snowfall),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        75 -> Icon(
            painter = painterResource(R.drawable.snowfall),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        77 -> Icon(
            painter = painterResource(R.drawable.snowfall),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        80 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        81 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        82 -> Icon(
            painter = painterResource(R.drawable.rainy),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        85 -> Icon(
            painter = painterResource(R.drawable.snowfall),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        86 -> Icon(
            painter = painterResource(R.drawable.snowfall),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        95 -> Icon(
            painter = painterResource(R.drawable.thunderstorm),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        96 -> Icon(
            painter = painterResource(R.drawable.thunderstorm),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        99 -> Icon(
            painter = painterResource(R.drawable.thunderstorm),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )

        else -> Icon(
            painter = painterResource(R.drawable.clear_sky),
            contentDescription = "Clear sky",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )
    }
}

@Composable
fun additionalParameters(
    title: String,
    result: String,
    image: @Composable (() -> Unit)? = null
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent.copy(alpha = 0.1f)
        ),
        modifier = Modifier
            .height(screenWidth * 0.45f)
            .width(screenWidth * 0.46f)
            .padding(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, color = Color.LightGray)
            Text(
                result,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500),
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                image?.invoke()
            }

        }

    }
}


fun extractHourlyTimePart(dateTimeList: List<String>): List<String> {
    Log.d("HourlyData","Hourly Time >> $dateTimeList")
    return dateTimeList.map { dateTime ->
        dateTime.split("T").getOrNull(1) ?: ""
    }
}
//
//fun extractHourlyTimePart(dateTimeList: List<String>): List<String> {
//    val formatter = DateTimeFormatter.ofPattern("HH:mm")
//    val currentTime = LocalTime.now().hour.toString()
//    Log.d("", "What is the time bhai >> $currentTime")
//
//    val extractedTime = dateTimeList.map { dateTime ->
//        dateTime.split("T").getOrNull(1) ?: ""
//    }
//    Log.d("", "What is the time bhai 2 >> $extractedTime")
//    val finalData = extractedTime.indexOfFirst { predicate ->
//        predicate!="$currentTime:00"
//    }
//    Log.d("FinalData","What is final data >> $finalData")
//    return extractedTime.subList(20,20+24)
//}

fun extractHourlyTemperature(hourlyTemp: List<Double>): List<Double> {
    Log.d("HourlyData","Hourly Temperature >> $hourlyTemp")
    return hourlyTemp
}

fun extractWeatherCode(hourlyWeatherCode: List<Int>): List<Int> {
    Log.d("HourlyData","Weather code >> $hourlyWeatherCode")
    return hourlyWeatherCode
}