package com.example.medicalappuserapplicational.presentation.componets

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.medicalappuserapplicational.R

@Composable
fun ImageHolder(imageUrl: String?) {
    AsyncImage(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Image",
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .size(150.dp),
        placeholder = painterResource(R.drawable.loadingimage),
        error = painterResource(R.drawable.errorimage)
    )
}