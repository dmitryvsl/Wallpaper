package com.example.wallpaper.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.wallpaper.R

@Composable
fun CustomAsyncImage(
    asyncPainter: Painter,
    isLoading: Boolean,
    isError: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) LoadingPlaceholder()
        if (isError) Image(
            painter = painterResource(R.drawable.error),
            contentDescription = null
        )
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = asyncPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}