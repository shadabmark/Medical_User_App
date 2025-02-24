package com.example.medicalappuserapplicational.presentation.componets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.medicalappuserapplicational.domain.dataModel.GetAllProductResponseItem
import com.example.medicalappuserapplicational.ui.theme.PrimaryAquaBlue

@Composable
fun BottomSheetContent(
    product: GetAllProductResponseItem,
    navController: NavController,
    onDismiss: () -> Unit
) {
    Surface(modifier = Modifier.padding(16.dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = product.product_name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            ImageHolder(imageUrl = product.product_image)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price: ${product.price}",
                fontSize = 18.sp, fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Category: ${product.category}",
                fontSize = 16.sp, fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Stock: ${product.stock}",
                fontSize = 16.sp, fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onDismiss()
                    navController.navigate("user_order_screen/${product.product_name}/${product.product_id}/${product.price}/${product.category}/${product.expire_date} ")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryAquaBlue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "OrderNow")
            }
        }
    }
}