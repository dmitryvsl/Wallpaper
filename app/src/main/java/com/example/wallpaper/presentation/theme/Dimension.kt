package com.example.wallpaper.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.w3c.dom.Text

data class Dimension (
    val padding: Padding,
    )

data class Padding(
    val padding_0_25: Dp,
    val padding_0_5: Dp,
    val padding_0_75: Dp,
    val padding_1: Dp,
    val padding_1_25: Dp,
    val padding_1_5: Dp,
    val padding_1_75: Dp,
    val padding_2: Dp,
)

val sw320 = Dimension(
    padding = Padding(
        padding_0_25 = 3.dp,
        padding_0_5 = 6.dp,
        padding_0_75 = 9.dp,
        padding_1 = 12.dp,
        padding_1_25 = 15.dp,
        padding_1_5 = 18.dp,
        padding_1_75 = 21.dp,
        padding_2 = 24.dp,
    )
)

val sw600 = Dimension(
    padding = Padding(
        padding_0_25 = 4.dp,
        padding_0_5 = 8.dp,
        padding_0_75 = 12.dp,
        padding_1 = 16.dp,
        padding_1_25 = 20.dp,
        padding_1_5 = 24.dp,
        padding_1_75 = 28.dp,
        padding_2 = 32.dp,
    )
)

val sw840 = Dimension(
    padding = Padding(
        padding_0_25 = 5.dp,
        padding_0_5 = 10.dp,
        padding_0_75 = 15.dp,
        padding_1 = 20.dp,
        padding_1_25 = 25.dp,
        padding_1_5 = 30.dp,
        padding_1_75 = 35.dp,
        padding_2 = 40.dp
    )
)

internal val LocalDimension = staticCompositionLocalOf { sw600 }

val MaterialTheme.dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimension.current