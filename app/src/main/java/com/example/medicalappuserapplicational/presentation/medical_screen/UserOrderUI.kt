package com.example.medicalappuserapplicational.presentation.medical_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalappuserapplicational.R
import com.example.medicalappuserapplicational.presentation.viewModel.AppViewModel
import com.example.medicalappuserapplicational.presentation.navigation.Routes.MEDICAL_SCREEN
import com.example.medicalappuserapplicational.ui.theme.PrimaryAquaBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserOrderUI(
    navController: NavController,
    productName: String?,
    productId: String?,
    productPrice: String?,
    category: String?,
    expireDate: String?,
    viewModel: AppViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf(productName ?: "") }
    var id by remember { mutableStateOf(productId ?: "") }
    var price by remember { mutableStateOf(productPrice ?: "") }
    var cat by remember { mutableStateOf(category ?: "") }
    var date by remember { mutableStateOf(expireDate ?: "") }
    var qty by remember { mutableStateOf("1") }
    var isSuccess by remember { mutableStateOf(false) }

    val userOrderState = viewModel.userOrderState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(userOrderState.value) {
        userOrderState.value.data?.let {
            isSuccess = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "UserOrder",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(MEDICAL_SCREEN)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = null,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            )
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isSuccess) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Congratulation(navController)
                }
            } else {
                Spacer(modifier = Modifier.height(110.dp))

                listOf(
                    "ProductName" to name,
                    "ProductId" to id,
                    "Price" to price,
                    "Category" to cat,
                    "Quantity" to qty,
                    "ExpireDate" to date
                ).forEach { (label, value) ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = { newValue ->
                            when (label) {
                                "ProductName" -> name = newValue
                                "ProductId" -> id = newValue
                                "Price" -> price = newValue
                                "Category" -> cat = newValue
                                "Quantity" -> qty = newValue
                                "ExpireDate" -> date = newValue
                            }
                        },
                        label = { Text(text = label) },
                        modifier = Modifier.size(height = 62.dp, width = 330.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = {
                        viewModel.userOrder(name, id, price, cat, qty, date)
                    },
                    colors = ButtonDefaults.buttonColors(PrimaryAquaBlue),
                    modifier = Modifier.size(height = 45.dp, width = 324.dp)
                ) {
                    Text(text = "OrderNow")
                }
            }
        }
    }
}

@Composable
fun Congratulation(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.comp2),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Order Placed Successfully!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Green
            )
            Spacer(modifier = Modifier.height(22.dp))
            Button(
                onClick = { navController.navigate(MEDICAL_SCREEN) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryAquaBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Done!", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}