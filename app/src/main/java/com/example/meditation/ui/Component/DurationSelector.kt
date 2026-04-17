package com.example.meditation.ui.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DurationSelector(
    selectedMinutes: Int,
    onSelect: (Int) -> Unit
) {

    val durations = listOf( 5, 15, 20, 30, 60)

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(top = 20.dp)
    ) {

        durations.forEach { minutes ->

            val isSelected = minutes == selectedMinutes

            Box(
                modifier = Modifier
                    .background(
                        if (isSelected) Color(0xFFB26A00) else Color(0xFF1C2A3A),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onSelect(minutes) }
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${minutes}m",
                    color = if (isSelected) Color.Black else Color.White
                )
            }
        }
    }


}

