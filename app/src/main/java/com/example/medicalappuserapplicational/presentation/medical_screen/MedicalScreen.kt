package com.example.medicalappuserapplicational.presentation.medical_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalappuserapplicational.UserPreferenceManager
import com.example.medicalappuserapplicational.presentation.viewModel.AppViewModel
import com.example.medicalappuserapplicational.presentation.componets.MedicalScreenBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalScreen(navController: NavController, userPreferenceManager: UserPreferenceManager) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val viewModel: AppViewModel = hiltViewModel()
    val medicalScreenState by viewModel.getAllProductState.collectAsState()

    Scaffold(
        bottomBar = {
            MedicalScreenBottomBar(
                selectedIndex = selectedIndex,
                onItemSelected = { index -> selectedIndex = index }
            )
        }

    ) { innerPadding ->
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedIndex = selectedIndex,
            navController = navController,
            userPreferenceManager = userPreferenceManager
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier,
    selectedIndex: Int,
    navController: NavController,
    userPreferenceManager: UserPreferenceManager
) {
    when (selectedIndex) {
        0 -> HomeScreenUI(navController)
        1 -> OrderStatusUI(navController)
        2 -> UserStockUI()
        3 -> UserProfileUI(navController, userPreferenceManager = userPreferenceManager)
    }
}

data class NavItem(
    val level: String,
    val icon: ImageVector
)