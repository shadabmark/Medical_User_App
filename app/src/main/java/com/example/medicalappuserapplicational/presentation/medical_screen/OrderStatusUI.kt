package com.example.medicalappuserapplicational.presentation.medical_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalappuserapplicational.presentation.viewModel.AppViewModel
import com.example.medicalappuserapplicational.presentation.componets.OrderStatusCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderStatusUI(navController: NavController, viewModel: AppViewModel = hiltViewModel()) {
    val getUserOrderState by viewModel.getUserOrderState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "OrderStatus", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            when {
                getUserOrderState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                getUserOrderState.error != null -> {
                    Text(text = "Failed to load Product. Please try again")
                }

                else -> {
                    OrderStatusCard()
                }
            }
        }
    }
}
