package com.example.skysync.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skysync.dashBoard.ui.WeatherScreen
import com.example.skysync.onBoarding.ui.OnBoardingScreen
import kotlinx.serialization.Serializable

@Serializable
data object DashBoard

@Serializable
data object OnBoarding

@Composable
fun NavHostContainer() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = OnBoarding,
        builder = {
            composable<OnBoarding> {
                OnBoardingScreen(navController = navController)
            }
            composable<DashBoard> {
                WeatherScreen()
            }
        }
    )
}