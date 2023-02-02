package com.example.wallpaper.feature.category_detail.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.wallpaper.R
import com.example.wallpaper.core.components.ErrorScreen
import com.example.wallpaper.core.components.LoadingPlaceholder
import com.example.wallpaper.core.components.TopAppBar
import com.example.wallpaper.core.extension.shimmerEffect
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.core.model.DataState
import com.example.wallpaper.feature.category.presentation.theme.dimens
import com.example.wallpaper.feature.category_detail.domain.model.Image

private val imageWidth = 150.dp
private val imageHeight = 200.dp

@Composable
fun CategoryDetailScreen(
    viewModel: CategoryDetailViewModel = hiltViewModel(),
    categoryType: CategoryType,
    onBackClick: () -> Unit,
) {
    var isFirstEnter by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(isFirstEnter) {
        if (isFirstEnter) viewModel.fetchImages(categoryType)
        isFirstEnter = false
    }

    val images by viewModel.images.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }

        when (images) {
            is DataState.Loading -> ShimmerList()
            is DataState.Error -> ErrorScreen(onButtonClick = { viewModel.fetchImages(categoryType) })
            is DataState.Success -> ImageList((images as DataState.Success<List<Image>>).data)
        }

    }
}

@Composable
fun ImageList(images: List<Image>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = imageWidth)
    ) {
        items(items = images, key = { image -> image.hashCode() }) { image ->
            ImageCard(image.url)
        }
        item(key = "bottom_spacing", span = { GridItemSpan(currentLineSpan = this.maxLineSpan) }) {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
fun ImageCard(
    url: String
) {
    Surface(
        modifier = Modifier
            .padding(MaterialTheme.dimens.padding.padding_0_5)
            .fillMaxWidth()
            .height(imageHeight),
        shadowElevation = 1.dp,
        tonalElevation = 1.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Box(contentAlignment = Alignment.Center) {
            var isLoading by remember { mutableStateOf(false) }
            var isError by remember { mutableStateOf(false) }
            if (isLoading) LoadingPlaceholder()
            if (isError)  Image(painter =  painterResource(R.drawable.error), contentDescription = null)
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                onLoading = {
                    isLoading = true
                    isError  = false
                },
                onError = {
                    isLoading = false
                    isError  = true
                },
                onSuccess = {
                    isLoading = false
                }
            )
        }
    }
}

@Composable
fun ShimmerList() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // image count in column
        val columnSpan = (this.minWidth / imageWidth).toInt()
        // image count in row
        val rowSpan = (this.minHeight / imageHeight).toInt()

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            repeat(rowSpan) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(columnSpan) {
                        ShimmerImageCard()
                    }
                }
            }
        }
    }
}

@Composable
fun ShimmerImageCard() {
    Surface(
        modifier = Modifier
            .padding(MaterialTheme.dimens.padding.padding_0_5)
            .width(imageWidth)
            .height(imageHeight),
        shape = MaterialTheme.shapes.medium
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .shimmerEffect()
        ) {}
    }
}
