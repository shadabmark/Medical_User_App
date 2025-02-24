package com.example.medicalappuserapplicational.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.medicalappuserapplicational.UserPreferenceManager
import com.example.medicalappuserapplicational.presentation.viewModel.AppViewModel
import com.example.medicalappuserapplicational.presentation.medical_screen.HomeScreenUI
import com.example.medicalappuserapplicational.presentation.medical_screen.OrderStatusUI
import com.example.medicalappuserapplicational.presentation.medical_screen.SplashScreenUI
import com.example.medicalappuserapplicational.presentation.medical_screen.UserOrderUI
import com.example.medicalappuserapplicational.presentation.medical_screen.UserProfileUI
import com.example.medicalappuserapplicational.presentation.medical_screen.UserStockUI
import com.example.medicalappuserapplicational.presentation.medical_screen.AuthUI
import com.example.medicalappuserapplicational.presentation.medical_screen.MedicalScreen

@Composable
fun AppNavigation(
    userPreferenceManager: UserPreferenceManager
) {
    val navController = rememberNavController()
    val viewModel: AppViewModel = hiltViewModel()
    val userID by userPreferenceManager.userId.collectAsState(initial = null)

    NavHost(
        navController = navController, startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreenUI(navController = navController, userID)
        }
        composable("auth_screen") {
            AuthUI(navController = navController, viewModel)
        }
        composable("medical_screen") {
            MedicalScreen(navController = navController, userPreferenceManager)
        }
        composable("home_screen") {
            HomeScreenUI(navController = navController)
        }
        composable("user_order_status_screen") {
            OrderStatusUI(navController = navController)
        }
        composable("user_stock_screen") {
            UserStockUI()
        }
        composable("user_profile_screen") {
            UserProfileUI(navController = navController, userPreferenceManager = userPreferenceManager)
        }
        composable(
            "user_order_screen/{productName}/{productId}/{productPrice}/{category}/{expireDate}",
            arguments = listOf(
                navArgument("productName") { type = NavType.StringType },
                navArgument("productId") { type = NavType.StringType },
                navArgument("productPrice") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType },
                navArgument("expireDate") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            UserOrderUI(
                navController = navController,
                productName = backStackEntry.arguments?.getString("productName"),
                productId = backStackEntry.arguments?.getString("productId"),
                productPrice = backStackEntry.arguments?.getString("productPrice"),
                category = backStackEntry.arguments?.getString("category"),
                expireDate = backStackEntry.arguments?.getString("expireDate")
            )
        }
    }
}


