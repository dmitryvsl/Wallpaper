package com.example.wallpaper.core.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingPlaceholder(
    size: DpSize = DpSize(50.dp, 50.dp),
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    BoxWithConstraints(
        Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        val width = constraints.maxWidth.dp.value
        val height = constraints.maxHeight.dp.value

        val rectSize = width / 4f

        val quarterCoordinates = hashMapOf(
            Quarter.TOP_LEFT to Coordinate(x = width / 3f, height / 3f),
            Quarter.TOP_RIGHT to Coordinate(x = width / 3f + rectSize, height / 3f),
            Quarter.BOTTOM_LEFT to Coordinate(x = width / 3f, height / 3f + rectSize),
            Quarter.BOTTOM_RIGHT to Coordinate(x = width / 3f + rectSize, height / 3f + rectSize),
        )

        var firstRectQuarterState by remember { mutableStateOf(Quarter.TOP_LEFT) }
        val firstRectTransition =
            updateTransition(targetState = firstRectQuarterState, label = null)
        val firstRectX by firstRectTransition.animateFloat(label = "") { quarter ->
            quarterCoordinates[quarter]!!.x
        }
        val firstRectY by firstRectTransition.animateFloat(label = "") { quarter ->
            quarterCoordinates[quarter]!!.y
        }

        var secondRectQuarterState by remember { mutableStateOf(Quarter.TOP_RIGHT) }
        val secondRectTransition =
            updateTransition(targetState = secondRectQuarterState, label = null)
        val secondRectX by secondRectTransition.animateFloat(label = "") { quarter ->
            quarterCoordinates[quarter]!!.x
        }
        val secondRectY by secondRectTransition.animateFloat(label = "") { quarter ->
            quarterCoordinates[quarter]!!.y
        }

        var thirdRectQuarterState by remember { mutableStateOf(Quarter.BOTTOM_RIGHT) }
        val thirdRectTransition =
            updateTransition(targetState = thirdRectQuarterState, label = null)
        val thirdRectX by thirdRectTransition.animateFloat(label = "") { quarter ->
            quarterCoordinates[quarter]!!.x
        }
        val thirdRectY by thirdRectTransition.animateFloat(label = "") { quarter ->
            quarterCoordinates[quarter]!!.y
        }

        LaunchedEffect(Unit) {
            var currentRect = 2
            while (true) {
                when (currentRect) {
                    0 -> firstRectQuarterState = getNextQuarter(firstRectQuarterState)
                    1 -> secondRectQuarterState = getNextQuarter(secondRectQuarterState)
                    2 -> thirdRectQuarterState = getNextQuarter(thirdRectQuarterState)
                }
                currentRect = (currentRect - 1)
                if (currentRect < 0) currentRect = 2
                delay(500L)
            }
        }

        Canvas(Modifier.fillMaxSize()) {
            rotate(-45f, Offset(width / 2, height / 2)) {
                drawRect(
                    color =color,
                    size = Size(rectSize, rectSize),
                    topLeft = Offset(firstRectX, y = firstRectY),
                    style = Stroke(width = 2f)
                )
                drawRect(
                    color = color,
                    size = Size(rectSize, rectSize),
                    topLeft = Offset(secondRectX, y = secondRectY),
                    style = Stroke(width = 2f)
                )
                drawRect(
                    color = color,
                    size = Size(rectSize, rectSize),
                    topLeft = Offset(thirdRectX, y = thirdRectY),
                    style = Stroke(width = 2f)
                )
            }
        }

    }
}

private fun getNextQuarter(currentQuarter: Quarter): Quarter = when (currentQuarter) {
    Quarter.TOP_LEFT -> Quarter.TOP_RIGHT
    Quarter.TOP_RIGHT -> Quarter.BOTTOM_RIGHT
    Quarter.BOTTOM_LEFT -> Quarter.TOP_LEFT
    Quarter.BOTTOM_RIGHT -> Quarter.BOTTOM_LEFT

}

private data class Coordinate(
    val x: Float,
    val y: Float,
)

private enum class Quarter {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT
}