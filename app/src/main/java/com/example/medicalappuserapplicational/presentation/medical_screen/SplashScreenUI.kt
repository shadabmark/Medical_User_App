package com.example.medicalappuserapplicational.presentation.medical_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalappuserapplicational.presentation.navigation.Routes.AUTH_SCREEN
import com.example.medicalappuserapplicational.presentation.navigation.Routes.MEDICAL_SCREEN
import com.example.medicalappuserapplicational.presentation.navigation.Routes.SPLASH_SCREEN
import kotlinx.coroutines.delay

@Composable
fun SplashScreenUI(navController: NavController, userId: String?) {
    LaunchedEffect (userId){
        delay(3000)
        if(userId != null){
            navController.navigate(MEDICAL_SCREEN){
                popUpTo(SPLASH_SCREEN) { inclusive = true }
            }
        } else {
            navController.navigate(AUTH_SCREEN){
                popUpTo(SPLASH_SCREEN) { inclusive = true }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text( text =  "Loading......", fontSize = 24.sp)
    }
}