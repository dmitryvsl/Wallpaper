package com.example.wallpaper.feature.image_list.presentation

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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.wallpaper.R
import com.example.wallpaper.core.components.CustomAsyncImage
import com.example.wallpaper.core.components.ErrorScreen
import com.example.wallpaper.core.components.TopAppBar
import com.example.wallpaper.core.data.SharedPainter
import com.example.wallpaper.core.exception.ExceptionMapper
import com.example.wallpaper.core.extension.shimmerEffect
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.feature.category.presentation.theme.dimens
import com.example.wallpaper.feature.image_list.domain.model.Image

private val imageWidth = 150.dp
private val imageHeight = 300.dp

@Composable
fun ImageListScreen(
    viewModel: ImageListViewModel = hiltViewModel(),
    categoryType: CategoryType,
    onBackClick: () -> Unit,
    navigateToImageDetail: () -> Unit,
    onThemeChange: (Boolean) -> Unit,
) {
    var isFirstEnter by rememberSaveable { mutableStateOf(true) }
    val lazyGridState = rememberLazyGridState()
    val images by viewModel.data.collectAsState()

    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val endReached by remember {
        derivedStateOf {
            lazyGridState.firstVisibleItemIndex == (images.size - lazyGridState.layoutInfo.visibleItemsInfo.size)
        }
    }
    LaunchedEffect(endReached) {
        viewModel.fetchNextPage()
    }
    LaunchedEffect(isFirstEnter) {
        if (isFirstEnter) viewModel.fetchImages(categoryType)
        isFirstEnter = false
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = stringResource(getCategoryNameByCategoryType(categoryType)),
            onThemeChange = onThemeChange,
            navigationIcon = {
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
        )

        when {
            images.isNotEmpty() -> ImageList(
                state = lazyGridState,
                images = images,
                isItemsLoading = loading,
                itemLoadingError = error?.let { stringResource(ExceptionMapper.getDescription(it)) },
                onItemClick = { url, painter ->
                    SharedPainter.savePainter(painter)
                    SharedPainter.saveUrl(url)
                    navigateToImageDetail()
                }
            ) {
                viewModel.fetchNextPage()
            }

            loading -> ShimmerList()
            error != null -> ErrorScreen(
                errorDescription = stringResource(ExceptionMapper.getDescription(error!!)),
                onButtonClick = { viewModel.fetchImages(categoryType) }
            )
        }
    }
}

@Composable
fun ImageList(
    state: LazyGridState,
    images: List<Image>,
    isItemsLoading: Boolean = false,
    itemLoadingError: String? = null,
    onItemClick: (String, Painter?) -> Unit,
    onRetryClick: () -> Unit,
) {
    LazyVerticalGrid(
        state = state,
        columns = GridCells.Adaptive(minSize = imageWidth)
    ) {
        items(count = images.size, key = { image -> image.hashCode() }) { index ->
            ImageCard(images[index].url) { onItemClick(images[index].fullImageUrl, it) }
        }

        if (isItemsLoading) item(span = { GridItemSpan(this.maxLineSpan) }) { LoadingItem() }
        if (itemLoadingError != null) item(span = { GridItemSpan(this.maxLineSpan) }) {
            ErrorItem(
                message = itemLoadingError,
                onRetryClick = onRetryClick
            )
        }

        item(key = "bottom_spacing", span = { GridItemSpan(currentLineSpan = this.maxLineSpan) }) {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
fun ErrorItem(message: String, onRetryClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.padding.padding_0_5),
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.padding.padding_0_25))
        Button(
            onClick = onRetryClick
        ) {
            Text(
                text = stringResource(R.string.reload),
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun LoadingItem() {
    Row(
        Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageCard(
    url: String,
    onClick: (Painter?) -> Unit,
) {
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        onLoading = {
            isLoading = true
            isError = false
        },
        onError = {
            isLoading = false
            isError = true
        }
    )
    Surface(
        modifier = Modifier
            .padding(MaterialTheme.dimens.padding.padding_0_5)
            .fillMaxWidth()
            .height(imageHeight),
        shadowElevation = 1.dp,
        onClick = { onClick(painter.state.painter) },
        tonalElevation = 1.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        CustomAsyncImage(
            asyncPainter = painter,
            isLoading = isLoading,
            isError = isError
        )
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

private fun getCategoryNameByCategoryType(categoryType: CategoryType) = when(categoryType){
    CategoryType.Animals -> R.string.animals
    CategoryType.Music -> R.string.music
    CategoryType.Nature -> R.string.nature
    CategoryType.Fashion -> R.string.fashion
    CategoryType.Sports -> R.string.sports
    CategoryType.People -> R.string.people
}
