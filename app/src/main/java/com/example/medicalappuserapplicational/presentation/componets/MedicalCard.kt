package com.example.medicalappuserapplicational.presentation.componets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalappuserapplicational.domain.dataModel.GetAllProductResponseItem
import com.example.medicalappuserapplicational.ui.theme.DarkBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalCard(
    getAllProductResponseItem: GetAllProductResponseItem,
    navController: NavController
) {
    var shouldBottomSheetShow by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<GetAllProductResponseItem?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .size(176.dp)
            .clickable {
                selectedProduct = getAllProductResponseItem
                shouldBottomSheetShow = true
            },
        colors = CardDefaults.cardColors(containerColor = DarkBlue)
    ) {
        Row(modifier = Modifier.padding(start = 12.dp, top = 12.dp)) {
            ImageHolder(getAllProductResponseItem.product_image)
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = getAllProductResponseItem.product_name,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "Rs. ",
                        color = Color.White,
                        fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = getAllProductResponseItem.price.toString(),
                        color = Color.White,
                        fontSize = 18.sp, fontWeight = FontWeight.ExtraBold
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "CTY: ${getAllProductResponseItem.category}",
                    color = Color.White,
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Available:",
                        color = Color.Green,
                        fontSize = 16.sp, fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${getAllProductResponseItem.stock}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState
        ) {
            selectedProduct?.let { product ->
                BottomSheetContent(
                    product = product,
                    navController = navController,
                    onDismiss = {
                        coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) shouldBottomSheetShow = false
                        }
                    }
                )
            }
        }
    }
}