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
import com.example.meditation.viewmodel.SoundType

@Composable
fun SoundSelector(
    selected: SoundType,
    onSelect: (SoundType) -> Unit,
    onPreview: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1C2A3A), RoundedCornerShape(20.dp))
            .padding(4.dp)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (selected == SoundType.TICK) Color(0xFFB26A00) else Color.Transparent,
                    RoundedCornerShape(16.dp)
                )
                .clickable { onSelect(SoundType.TICK) }
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("TICK", color = Color.White)
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (selected == SoundType.BELL) Color(0xFFB26A00) else Color.Transparent,
                    RoundedCornerShape(16.dp)
                )
                .clickable { onSelect(SoundType.BELL) }
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("BELL", color = Color.White)
        }
    }

}
