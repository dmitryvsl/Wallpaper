package com.example.wallpaper.feature.image_detail.presentation

import android.Manifest
import android.app.WallpaperManager
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import com.example.wallpaper.R
import com.example.wallpaper.core.components.CustomAsyncImage
import com.example.wallpaper.core.components.LoadingPlaceholder
import com.example.wallpaper.core.components.LockScreenOrientation
import com.example.wallpaper.core.data.SharedPainter
import com.example.wallpaper.feature.category.presentation.theme.dimens
import com.example.wallpaper.feature.category_detail.presentation.ErrorItem
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ImageDetailScreen(
    onBackClick: () -> Unit
) {
    val context = LocalContext.current

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    var imagePainter by remember { mutableStateOf(SharedPainter.getPainter()) }
    var drawable: Drawable? by remember { mutableStateOf(null) }

    if (drawable != null) imagePainter = rememberDrawablePainter(drawable)

    var isHighQualityImageLoading by remember { mutableStateOf(true) }
    var isHighQualityImageLoadingError by remember { mutableStateOf(false) }

    var url = remember { SharedPainter.getUrl() }

    var wallpaperBitmap: Bitmap? by remember { mutableStateOf(null) }

    val request: ImageRequest = ImageRequest.Builder(context)
        .data(url)
        .listener(
            onError = { _, _ ->
                isHighQualityImageLoadingError = true
                isHighQualityImageLoading = false
            },
            onSuccess = { _, success ->
                drawable = success.drawable.current
                wallpaperBitmap = success.drawable.current.toBitmap()
                isHighQualityImageLoading = false
                url = null
            }
        )
        .build()
    var imageRequestDisposable = context.imageLoader.enqueue(request)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        when {
            isHighQualityImageLoadingError -> {
                imageRequestDisposable.dispose()
                ErrorItem(message = stringResource(R.string.errorOccured)) {
                    imageRequestDisposable = context.imageLoader.enqueue(request)
                    isHighQualityImageLoadingError = false
                    isHighQualityImageLoading = true
                }
            }

            imagePainter == null && isHighQualityImageLoading -> LoadingPlaceholder()

            imagePainter != null -> ImageAndProgressBar(
                imagePainter = imagePainter!!,
                showProgressBar = isHighQualityImageLoading,
                wallpaperBitmap = wallpaperBitmap
            )

        }

        OutlinedIconButton(
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopStart),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = stringResource(R.string.back),
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
fun BoxScope.ImageAndProgressBar(
    imagePainter: Painter,
    showProgressBar: Boolean,
    wallpaperBitmap: Bitmap?
) {
    CustomAsyncImage(
        asyncPainter = imagePainter,
        isLoading = false,
        isError = false
    )
    if (showProgressBar) CircularProgressIndicator()
    else if (wallpaperBitmap != null) SetAsWallpaper(wallpaperBitmap)

}

@Composable
fun BoxScope.SetAsWallpaper(
    wallpaperBitmap: Bitmap?
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isWallpaperSettingUp by remember { mutableStateOf(false) }
    var isWallpaperSetUp by remember { mutableStateOf(false) }

    val setWallpaper = {
        if (wallpaperBitmap != null) {
            coroutineScope.launch(Dispatchers.IO) {
                isWallpaperSettingUp = true
                val wallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(wallpaperBitmap)
                isWallpaperSetUp = true
                isWallpaperSettingUp = false
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            setWallpaper()
        }
    }

    when {
        isWallpaperSettingUp -> CircularProgressIndicator(
            Modifier
                .navigationBarsPadding()
                .align(Alignment.BottomCenter)
        )

        isWallpaperSetUp ->
            Surface(
                modifier = Modifier
                    .navigationBarsPadding()
                    .align(Alignment.BottomCenter),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    modifier = Modifier.padding(MaterialTheme.dimens.padding.padding_0_5),
                    text = stringResource(R.string.imageNowWallpaper),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

        else -> Button(
            modifier = Modifier
                .navigationBarsPadding()
                .align(Alignment.BottomCenter),
            shape = MaterialTheme.shapes.small,
            onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.SET_WALLPAPER
                    ) -> setWallpaper()

                    else -> launcher.launch(Manifest.permission.SET_WALLPAPER)
                }
            }
        ) {
            Text(
                text = stringResource(R.string.setAsWallpaper),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

