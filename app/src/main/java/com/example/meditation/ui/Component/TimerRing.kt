package com.example.meditation.ui.Component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun TimerRing(
    progress: Float,
    modifier: Modifier = Modifier
) {

    // 🔄 Smooth animation
    val animatedProgress = animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 500),
        label = ""
    ).value

    val strokeWidth = 12.dp

    Canvas(
        modifier = modifier.size(260.dp)
    ) {

        // 🔘 INNER DOTTED CIRCLE
        val inset = 20.dp.toPx()

        drawArc(
            color = Color(0x55FFFFFF), // 👈 soft white dots
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = androidx.compose.ui.geometry.Offset(inset, inset),
            size = androidx.compose.ui.geometry.Size(
                size.width - inset * 2,
                size.height - inset * 2
            ),
            style = Stroke(
                width = 2.dp.toPx(),  // 👈 thin
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(1f, 20f) // 👈 VERY SMALL dot, tight spacing
                )
            )
        )

        // ⚫ BACKGROUND RING
        drawArc(
            color = Color(0xFF1C2A3A),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        )

        // ✨ SOFT GLOW (subtle)
        drawArc(
            color = Color(0x22FF9800),
            startAngle = -90f,
            sweepAngle = 360f * animatedProgress,
            useCenter = false,
            style = Stroke(
                width = (strokeWidth * 1.6f).toPx(),
                cap = StrokeCap.Round
            )
        )

        // 🟠 MAIN PROGRESS RING
        drawArc(
            color = Color(0xFFB26A00),
            startAngle = -90f,
            sweepAngle = 360f * animatedProgress,
            useCenter = false,
            style = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}