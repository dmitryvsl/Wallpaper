package com.example.wallpaper.feature.category.presentation

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wallpaper.R
import com.example.wallpaper.core.components.TopAppBar
import com.example.wallpaper.core.model.CategoryType
import com.example.wallpaper.feature.category.presentation.theme.dimens

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
    isScreenSizeCompact: Boolean,
    navigateToCategoryDetail: (CategoryType) -> Unit,
    onThemeChange: (Boolean) -> Unit,
) {
    val categories by viewModel.categories.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(onThemeChange = onThemeChange)
        if (isScreenSizeCompact)
            CategoryColumnList(
                categories = categories,
                onCategorySelected = navigateToCategoryDetail
            )
        else
            CategoryVerticalGrid(
                categories = categories,
                onCategorySelected = navigateToCategoryDetail
            )

    }
}

@Composable
fun CategoryColumnList(
    categories: List<Category>,
    onCategorySelected: (CategoryType) -> Unit
) {
    LazyColumn {
        item(key = "select_category") {
            Text(
                modifier = Modifier
                    .padding(vertical = MaterialTheme.dimens.padding.padding_0_5)
                    .padding(start = MaterialTheme.dimens.padding.padding_1),
                text = stringResource(R.string.selectCategory),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        items(items = categories, key = { category -> category.title }) { category ->
            CategoryCard(
                category = category,
                contentScale = ContentScale.FillWidth
            ) { onCategorySelected(category.type) }
        }
        item(key = "bottom_padding") {
            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

@Composable
fun CategoryVerticalGrid(
    categories: List<Category>,
    onCategorySelected: (CategoryType) -> Unit
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
                text = stringResource(R.string.selectCategory),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        items(items = categories, key = { category -> category.title }) { category ->
            CategoryCard(category = category, height = 150.dp) { onCategorySelected(category.type) }
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
    contentScale: ContentScale = ContentScale.FillBounds,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.dimens.padding.padding_0_5)
            .height(height)
            .padding(horizontal = MaterialTheme.dimens.padding.padding_1)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.DarkGray)
            .clickable { onClick() },
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