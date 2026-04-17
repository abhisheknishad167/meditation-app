package com.example.meditation.ui.Component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun BreathingOrb(
    isRunning: Boolean,
    isCompleted: Boolean = false
) {

    val infiniteTransition = rememberInfiniteTransition(label = "")

    // 🌬️ Breathing scale (inhale/exhale feel)
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    // 🌫️ Faint opacity animation
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    val color = if (isCompleted) {
        Color(0xFF4CAF50) // Green on completion
    } else {
        Color(0xFFFF9800) // Orange
    }

    Box(
        modifier = Modifier
            .size(50.dp) // 🔥 smaller = cleaner look (was 50.dp)
            .graphicsLayer {
                scaleX = if (isRunning) scale else 1f
                scaleY = if (isRunning) scale else 1f
            }
            .shadow(
                elevation = 30.dp,
                shape = CircleShape,
                ambientColor = color.copy(alpha = 0.3f),
                spotColor = color.copy(alpha = 0.3f)
            )
            .background(
                color.copy(alpha = if (isRunning) alpha else 0.3f),
                CircleShape
            )
    )
}