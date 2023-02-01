package com.example.wallpaper.presentation.category

import android.app.Activity
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass.Companion.Medium
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wallpaper.R
import com.example.wallpaper.presentation.MainActivity
import com.example.wallpaper.presentation.theme.WallpaperTheme
import com.example.wallpaper.presentation.theme.dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass
) {
    val categories by viewModel.categories.collectAsState()
    val isScreenSizeMedium = remember { windowSizeClass.widthSizeClass == WindowWidthSizeClass.Medium }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            modifier = Modifier.shadow(elevation = 4.dp),
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            title = {
                Text(
                    text = stringResource(R.string.wallpaper),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        )
        if (isScreenSizeMedium)
            CategoryVerticalGrid(categories = categories)
        else
            CategoryColumnList(categories = categories)

    }
}

@Composable
fun CategoryColumnList(
    categories: List<Category>
) {
    LazyColumn {
        item(key = "select_category") {
            Text(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.padding.padding_0_5)
                    .padding(start = MaterialTheme.dimens.padding.padding_1),
                text = "Select Category",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        items(items = categories, key = { category -> category.title }) { category ->
            CategoryCard(category = category, contentScale = ContentScale.FillWidth)
        }
        item(key = "bottom_padding") {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
fun CategoryVerticalGrid(
    categories: List<Category>
) {
    val columnCount = 2
    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        horizontalArrangement = Arrangement.Center
    ) {
        item(key = "select_category", span = { GridItemSpan(columnCount) }) {
            Text(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.padding.padding_0_5)
                    .padding(start = MaterialTheme.dimens.padding.padding_1),
                text = "Select Category",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        items(items = categories, key = { category -> category.title }) { category ->
            CategoryCard(category = category, height = 150.dp)
        }
        item(key = "bottom_padding", span = { GridItemSpan(columnCount) }) {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}


@Composable
fun CategoryCard(
    category: Category,
    height: Dp = 100.dp,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.padding.padding_0_5)
            .height(height)
            .padding(horizontal = MaterialTheme.dimens.padding.padding_1)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.DarkGray)
            .clickable { },
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(category.image),
            contentDescription = stringResource(R.string.category),
            contentScale = contentScale
        )
        Text(
            modifier = Modifier.padding(start = MaterialTheme.dimens.padding.padding_0_75),
            text = stringResource(category.title),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CategoryScreenPreview() {
    WallpaperTheme {
        CategoryScreen(
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(840.dp, 1280.dp)))
    }
}

