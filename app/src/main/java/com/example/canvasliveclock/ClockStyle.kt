package com.example.canvasliveclock

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class ClockStyle(
    val radius: Float = 400f,
    val normalLineLength: Float = 20f,
    val longLineLength: Float = 30f,
    val normalLineColor: Color = Color.Gray,
    val longLineColor: Color = Color.Black,
    val textSize: TextUnit = 15.sp,
)
