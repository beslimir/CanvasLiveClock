package com.example.canvasliveclock

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

@Composable
fun CanvasClock(
    modifier: Modifier = Modifier,
    style: ClockStyle = ClockStyle(),
    minDegree: Int = 0,
    maxDegree: Int = 359,
) {
    val radius = style.radius
    var center by remember {
        mutableStateOf(Offset.Zero)
    }
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }
    var angle by remember {
        mutableStateOf(0f)
    }

    Canvas(
        modifier = modifier
    ) {
        center = this.center
        circleCenter = Offset(
            x = center.x,
            y = center.y
        )

        drawCircle(
            color = Color.LightGray,
            radius = radius,
            center = circleCenter
        )

        for (i in minDegree..maxDegree step 6) {
            val angleInRad = (i - 90 + angle) * (PI / 180f).toFloat()

            val lineLength = when {
                i % 10 == 0 -> style.longLineLength
                else -> style.normalLineLength
            }
            val lineColor = when {
                i % 10 == 0 -> style.longLineColor
                else -> style.normalLineColor
            }
            val lineStart = Offset(
                x = (radius - lineLength) * cos(angleInRad) + circleCenter.x,
                y = (radius - lineLength) * sin(angleInRad) + circleCenter.y
            )
            val lineEnd = Offset(
                x = radius * cos(angleInRad) + circleCenter.x,
                y = radius * sin(angleInRad) + circleCenter.y
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1f
            )
        }

        val secLineLength = radius - 60f
        val secLineStart = Offset(
            x = circleCenter.x,
            y = circleCenter.y
        )
        val secLineEnd = Offset(
            x = circleCenter.x + secLineLength,
            y = circleCenter.y
        )

        drawLine(
            color = Color.Red,
            start = secLineStart,
            end = secLineEnd,
            strokeWidth = 1f
        )



    }

}