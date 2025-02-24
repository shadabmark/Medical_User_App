package com.example.medicalappuserapplicational.presentation.componets

import android.util.Log
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun StockCard(viewModel: AppViewModel = hiltViewModel()) {
    val userStockState by viewModel.getUserStockState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(userStockState.data) { data ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DarkBlue,
                    contentColor = Color.White
                )
            ) {
                val productInfo = listOf(
                    "Product_Name" to data.product_name,
                    "Category" to data.category,
                    "Price" to data.price,
                    "Stock" to data.stock,
                    "ExpireDate" to data.expire_date
                )
                Log.d("Stock", "Status ${data.product_name}")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    productInfo.forEach { (label, value) ->
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
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

        }
        item { Spacer(modifier = Modifier.height(76.dp)) }
    }
}