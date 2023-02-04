package com.example.wallpaper.core.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.wallpaper.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navigationIcon: @Composable () -> Unit = {}
) {
    androidx.compose.material3.TopAppBar(
        modifier = Modifier
            .shadow(elevation = 4.dp)
            .zIndex(1f),
        navigationIcon = navigationIcon,
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
}