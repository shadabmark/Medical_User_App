package com.example.medicalappuserapplicational.presentation.medical_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalappuserapplicational.R
import com.example.medicalappuserapplicational.UserPreferenceManager
import com.example.medicalappuserapplicational.presentation.viewModel.AppViewModel
import com.example.medicalappuserapplicational.presentation.navigation.Routes.AUTH_SCREEN
import com.example.medicalappuserapplicational.ui.theme.PrimaryAquaBlue
import com.example.medicalappuserapplicational.ui.theme.SecondarySoftGreen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileUI(
    navController: NavController,
    viewModel: AppViewModel = hiltViewModel(),
    userPreferenceManager: UserPreferenceManager
) {
    LaunchedEffect(Unit) {
        viewModel.fetchCurrentUser()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Profile", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ProfileUI(navController, userPreferenceManager = userPreferenceManager)
        }
    }
}

@Composable
fun ProfileUI(
    navController: NavController,
    viewModel: AppViewModel = hiltViewModel(),
    userPreferenceManager: UserPreferenceManager
) {
    val userState by viewModel.getSpecificUserState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when {
            userState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            userState.error != null -> Text(text = "Failed to load user. Please try again.")
            userState.data != null -> {
                val user = userState.data!!
                Spacer(modifier = Modifier.height(34.dp))
                Image(
                    painter = painterResource(R.drawable.pp),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(56.dp))

                val userFields = listOf(
                    "Username" to user.name,
                    "Email" to user.email,
                    "Phone Number" to user.phone_number,
                    "Address" to user.address,
                    "PinCode" to user.pinCode,
                    "Account Creation" to user.date_of_account_creation
                )

                userFields.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowItems.forEach { (label, value) ->
                            OutlinedTextField(
                                value = value,
                                onValueChange = {},
                                label = { Text(label) },
                                readOnly = true,
                                modifier = Modifier
                                    .size(height = 74.dp, width = 175.dp)
                                    .padding(vertical = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondarySoftGreen)
                ) {
                    Text("Edit Profile", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { showDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryAquaBlue)
                ) {
                    Text("Log Out", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                // Show Confirmation Dialog
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        text = {
                            Text(
                                text = "Are you sure ?",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        confirmButton = {},
                        dismissButton = {
                                TextButton(
                                    onClick = { showDialog = false }
                                ) {
                                    Text("Cancel")
                                }
                                TextButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            userPreferenceManager.clearUserID()
                                            navController.navigate(AUTH_SCREEN) {
                                                popUpTo(0)
                                            }
                                        }
                                        showDialog = false
                                    }
                                ) {
                                    Text("Sure", fontWeight = FontWeight.Bold)
                                }

                        }
                    )
                }
            }

            else -> Text(text = "No user found.")
        }
    }
}


