package com.example.canvasliveclock

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
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

    val milliseconds = remember {
        System.currentTimeMillis()
    }
    var seconds by remember {
        mutableStateOf((milliseconds / 1000f) % 60f)
    }
    var minutes by remember {
        mutableStateOf((milliseconds / 1000f / 60f) % 60f)
    }
    var hours by remember {
        mutableStateOf(milliseconds / 1000f / 3600f % 24 + 1f)
    }

    LaunchedEffect(key1 = seconds) {
        delay(1000L)
        seconds += 1f
        if (seconds >= 60f) seconds = 0f
        minutes += 1f / 60f
        if (minutes >= 60f) minutes = 0f
        hours += 1f / (60f * 12f)
        if (hours >= 13f) hours = 1f
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
            val angleInRad = (i - 90) * (PI / 180f).toFloat()

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

        //SECONDS
        val secLineLength = radius - 60f
        val secLineStart = Offset(
            x = circleCenter.x - 20f,
            y = circleCenter.y
        )
        val secLineEnd = Offset(
            x = circleCenter.x + secLineLength + 20f,
            y = circleCenter.y
        )

        rotate(degrees = seconds * (360f / 60f)) {
            drawLine(
                color = Color.Red,
                start = secLineStart,
                end = secLineEnd,
                strokeWidth = 1f,
                cap = StrokeCap.Round
            )
        }

        //MINUTES
        val minLineLength = radius - 60f
        val minLineStart = Offset(
            x = circleCenter.x,
            y = circleCenter.y
        )
        val minLineEnd = Offset(
            x = circleCenter.x + minLineLength,
            y = circleCenter.y
        )

        rotate(degrees = minutes * (360f / 60f)) {
            drawLine(
                color = Color.Black,
                start = minLineStart,
                end = minLineEnd,
                strokeWidth = 2f,
                cap = StrokeCap.Round
            )
        }

        //HOURS
        val hourLineLength = radius / 2f + 20f
        val hourLineStart = Offset(
            x = circleCenter.x,
            y = circleCenter.y
        )
        val hourLineEnd = Offset(
            x = circleCenter.x + hourLineLength,
            y = circleCenter.y
        )

        rotate(degrees = hours * (360f / 60f)) {
            drawLine(
                color = Color.Black,
                start = hourLineStart,
                end = hourLineEnd,
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
        }

        Log.d("Current time", "$hours:$minutes:$seconds ==> $milliseconds")


    }

}