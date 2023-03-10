package com.example.wallpaper.core.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.wallpaper.R
import com.example.wallpaper.app.theme.LocalThemeInDark
import com.example.wallpaper.app.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String = stringResource(R.string.wallpaper),
    navigationIcon: @Composable () -> Unit = {},
    onThemeChange: (Boolean) -> Unit
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
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        actions = {
            val checked = LocalThemeInDark.current
            Switch(
                checked = checked,
                onCheckedChange = onThemeChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color(0xff003892),
                    uncheckedThumbColor = Color(0xff003892),
                ),
                thumbContent = {
                    if (checked)
                        Icon(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(R.drawable.ic_moon),
                            contentDescription = null,
                            tint = White
                        )
                    else Icon(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(R.drawable.ic_sun),
                        contentDescription = null,
                        tint = White
                    )

                }
            )
        }
    )
}