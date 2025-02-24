package com.example.medicalappuserapplicational.presentation.componets

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medicalappuserapplicational.presentation.viewModel.AppViewModel
import com.example.medicalappuserapplicational.ui.theme.DarkBlue

@Composable
fun OrderStatusCard(viewModel: AppViewModel = hiltViewModel()) {
    val getUserOrderState by viewModel.getUserOrderState.collectAsState()
    val deleteState by viewModel.deleteUserOrderState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(deleteState) {
        if (deleteState.data != null) {
            Toast.makeText(context, "Cancel Order Successfully", Toast.LENGTH_SHORT).show()
            viewModel.getUserOrderProduct()
        } else if (deleteState.error != null) {
            Toast.makeText(context, "Error: ${deleteState.error}", Toast.LENGTH_SHORT).show()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(getUserOrderState.data) { data ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                val orderStatusText =
                    if (data.order_status == 0) "Pending" else "Success"
                val orderStatusColor =
                    if (data.order_status == 0) Color.Yellow else Color.Green


                val orderInfo = listOf(
                    "Product_Name" to data.product_name,
                    "Order_Id" to data.order_id,
                    "Category" to data.category,
                    "Price" to data.price,
                    "Quantity" to data.quantity,
                    "ExpireDate" to data.expire_date
                )
                Log.d("Order", "Status ${data.product_name}")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    orderInfo.forEach { (label, value) ->
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("$label: ")
                                }
                                append(value.toString())
                            },
                            fontSize = 17.sp
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                ) {
                                    append("Order_Status: ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = orderStatusColor
                                    )
                                ) {
                                    append(orderStatusText)
                                }
                            },
                            fontSize = 17.sp
                        )
                        if (data.order_status == 0) {

                            TextButton(
                                onClick = {
                                    viewModel.deleteUserOrder(data.order_id)
                                }
                            ) {
                                Text(
                                    text = "CANCEL", fontSize = 18.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFFFF4C4C)
                                )
                            }
                        }
                    }
                }

            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        item { Spacer(modifier = Modifier.height(76.dp)) }
    }
}