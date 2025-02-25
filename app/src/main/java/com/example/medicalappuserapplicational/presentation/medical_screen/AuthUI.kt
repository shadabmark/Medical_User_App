package com.example.medicalappuserapplicational.presentation.medical_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalappuserapplicational.R
import com.example.medicalappuserapplicational.presentation.viewModel.AppViewModel
import com.example.medicalappuserapplicational.presentation.navigation.Routes.MEDICAL_SCREEN
import com.example.medicalappuserapplicational.ui.theme.PrimaryAquaBlue


@Composable
fun AuthUI(navController: NavController, viewModel: AppViewModel = hiltViewModel()) {

    var selectedButton by remember { mutableStateOf("Login") }
    val state = viewModel.signUpState.collectAsState()
    val loginState = viewModel.loginUserState.collectAsState()
    var userName by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPhoneNumber by remember { mutableStateOf("") }
    var userAddress by remember { mutableStateOf("") }
    var userPinCode by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(state.value) {
        state.value.data?.let {
            Toast.makeText(context, "Signup Success", Toast.LENGTH_SHORT).show()

            // Reset all text fields after successful signup
            userName = ""
            userPassword = ""
            userEmail = ""
            userPhoneNumber = ""
            userAddress = ""
            userPinCode = ""
        }
    }

    LaunchedEffect(loginState.value.error) {
        loginState.value.error?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    when {
        loginState.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        loginState.value.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = loginState.value.error.toString())
            }
        }

        loginState.value.data != null -> {
            val userId = loginState.value.data!!.message

            LaunchedEffect(loginState.value.data) {
                loginState.value.data?.let {
                     if (it.message == userId) {
                        navController.navigate(MEDICAL_SCREEN)
                    }
                    Log.d("Status", "message: ${it.message}")
                }
            }

            if (loginState.value.data?.message != userId) {
                Toast.makeText(
                    context,
                    "Login Failed: ${loginState.value.data?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(R.drawable.medicalicon), contentDescription = null,
                modifier = Modifier.size(46.dp)
            )
        }
        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { selectedButton = "Login" },
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp),
                        elevation = ButtonDefaults.buttonElevation(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedButton == "Login") PrimaryAquaBlue else Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Login")
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { selectedButton = "SignUp" },
                        shape = RectangleShape,
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp),
                        elevation = ButtonDefaults.buttonElevation(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedButton == "SignUp") PrimaryAquaBlue else Color.LightGray,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "SignUp")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (selectedButton == "SignUp") {
                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        singleLine = true,
                        placeholder = { Text(text = "Full Name") },
                        modifier = Modifier.size(height = 55.dp, width = 300.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                TextField(
                    value = userEmail,
                    onValueChange = { userEmail = it },
                    singleLine = true,
                    placeholder = { Text(text = "Email Address") },
                    modifier = Modifier.size(height = 55.dp, width = 300.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = userPassword,
                    onValueChange = { userPassword = it },
                    singleLine = true,
                    placeholder = { Text(text = "Enter Password") },
                    modifier = Modifier.size(height = 55.dp, width = 300.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
                if (selectedButton == "SignUp") {
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = userPhoneNumber,
                        onValueChange = { userPhoneNumber = it },
                        singleLine = true,
                        placeholder = { Text("PhoneNumber") },
                        modifier = Modifier.size(height = 55.dp, width = 300.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = userAddress,
                        onValueChange = { userAddress = it },
                        singleLine = true,
                        placeholder = { Text(text = "Location") },
                        modifier = Modifier.size(height = 55.dp, width = 300.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextField(
                        value = userPinCode,
                        onValueChange = { userPinCode = it },
                        placeholder = { Text(text = "PINCode") },
                        modifier = Modifier.size(height = 55.dp, width = 300.dp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(42.dp))
                    Button(
                        onClick = {
                            viewModel.signUp(
                                name = userName,
                                password = userPassword,
                                email = userEmail,
                                phoneNumber = userPhoneNumber,
                                address = userAddress,
                                pinCode = userPinCode
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryAquaBlue,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(height = 42.dp, width = 300.dp)
                    ) {
                        Text(text = "SignUp")
                    }
                }
                if (selectedButton == "Login") {
                    Spacer(modifier = Modifier.height(42.dp))
                    Button(
                        onClick = {
                            viewModel.userLogin(
                                email = userEmail,
                                password = userPassword
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryAquaBlue,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.size(height = 42.dp, width = 300.dp)
                    ) {
                        Text(text = "Login")
                    }
                }
                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}