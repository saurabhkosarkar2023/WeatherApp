package com.example.skysync.onBoarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.skysync.R
import com.example.skysync.navigation.DashBoard

@Composable
fun OnBoardingScreen(
    navController: NavController
) {
    Scaffold(
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Image(
                painter = painterResource(R.drawable.onboard_bg_black),
                contentDescription = "bg_image",
                modifier = Modifier.fillMaxSize()
            )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    navController.navigate(DashBoard)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Continue")
            }
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//private fun OnBoardingPrev() {
//    OnBoardingScreen()
//}