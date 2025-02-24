package com.example.medicalappuserapplicational.presentation.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalGroceryStore
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.medicalappuserapplicational.presentation.medical_screen.NavItem

@Composable
fun MedicalScreenBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val navItemList = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Status", Icons.Filled.ShoppingBasket),
        NavItem("UserStock", Icons.Filled.LocalGroceryStore),
        NavItem("Profile", Icons.Filled.Person2)
    )

    NavigationBar(
        modifier = Modifier
            .height(85.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(Color(0xFF6FC1FF), Color(0xFF3498DB))
                )
            ),
        containerColor = Color.Transparent
    ) {
        navItemList.forEachIndexed { index, navItem ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    onItemSelected(index)
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = null,
                        modifier = Modifier.padding(vertical = 2.dp),
                    )
                },
                label = {
                    Text(
                        text = navItem.level,
                        color = if (selectedIndex == index) Color.White else Color.LightGray
                    )
                }, alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.LightGray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color(0xFF0D47A1)
                )
            )
        }
    }
}