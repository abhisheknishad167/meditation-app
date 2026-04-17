package com.example.meditation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(

    // 🕒 Timer text (big number)
    displayLarge = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 44.sp,
        letterSpacing = 1.sp
    ),

    // 📝 Status text (PAUSED / RUNNING)
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 2.sp
    )
)