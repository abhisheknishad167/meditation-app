package com.example.meditation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.meditation.ui.Component.*
import com.example.meditation.ui.theme.MeditationTheme
import com.example.meditation.viewmodel.TimerViewModel
import com.example.meditation.domain.audio.SoundManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 FULLSCREEN MODE
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(
            WindowInsetsCompat.Type.statusBars() or
                    WindowInsetsCompat.Type.navigationBars()
        )
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        enableEdgeToEdge()

        setContent {
            MeditationTheme {

                val viewModel: TimerViewModel = viewModel()
                val state by viewModel.state.collectAsState()

                val context = LocalContext.current
                val soundManager = remember { SoundManager(context) }
                viewModel.setSoundManager(soundManager)

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF0B1A2A),
                                        Color(0xFF02080F)
                                    )
                                )
                            )
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(0.dp))

                        // 🔤 TITLE
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "TRANQUIL",
                                color = Color(0xFFB0BEC5),
                                letterSpacing = 6.sp,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "MEDITATION TIMER",
                                color = Color(0xFF7A8A99),
                                fontSize = 12.sp,
                                letterSpacing = 4.sp,   // 👈 THIS creates spacing effect
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))

                        // 🟠 TIMER SECTION

                        Box(
                            modifier = Modifier.size(260.dp),
                            contentAlignment = Alignment.Center
                        ) {

                            TimerRing(progress = state.progress)

                            // CENTER CONTENT
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {

                                Text(
                                    text = formatTime(state.remainingTime),
                                    color = Color.White,
                                    fontSize = 44.sp,
                                    fontWeight = FontWeight.Light,   // 👈 IMPORTANT
                                    letterSpacing = 1.sp             // 👈 subtle spacing
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = if (state.isRunning) "RUNNING" else "PAUSED",
                                    color = Color(0xFF7A8A99),
                                    fontSize = 12.sp,
                                    letterSpacing = 2.sp,           // 👈 spaced like design
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(20.dp))

                               /* BreathingOrb(
                                    isRunning = state.isRunning,
                                    isCompleted = state.isCompleted
                                )*/
                            }

                            // 🟠 BREATHING ORB (OUTSIDE RING)
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .align(Alignment.BottomCenter)
                                    .offset(y = 90.dp),   // 👈 adjust this
                                contentAlignment = Alignment.Center
                            ) {
                                BreathingOrb(
                                    isRunning = state.isRunning,
                                    isCompleted = state.isCompleted
                                )
                            }

                            // 🔥 BUTTONS (FIXED ALIGNMENT)
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .offset(y = 190.dp),
                                horizontalArrangement = Arrangement.spacedBy(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                // Reset
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .shadow(8.dp, CircleShape)   // 👈 ADD THIS LINE
                                        .background(Color(0xFF1C2A3A), CircleShape)
                                        .clickable { viewModel.reset() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("↺", color = Color.White)
                                }

                                // Play
                                PlayButton(
                                    isRunning = state.isRunning,
                                    onClick = { viewModel.toggleTimer() }
                                )

                                // Sound Preview
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .shadow(8.dp, CircleShape)   // 👈 ADD THIS
                                        .background(Color(0xFF1C2A3A), CircleShape)
                                        .clickable { viewModel.previewSound() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("♪", color = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(200.dp))

                        // ⚙️ SETTINGS PANEL
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF02080F))
                                .padding(16.dp)
                        ) {

                            Text("SETTINGS", color = Color.Gray, fontSize = 12.sp)

                            Spacer(modifier = Modifier.height(10.dp))

                            Text("DURATION", color = Color.Gray, fontSize = 12.sp)

                            Spacer(modifier = Modifier.height(10.dp))

                            DurationSelector(
                                selectedMinutes = state.selectedMinutes,
                                onSelect = { viewModel.setDuration(it) }
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text("SOUND PER SECOND", color = Color.Gray, fontSize = 12.sp)

                            Spacer(modifier = Modifier.height(10.dp))

                            SoundSelector(
                                selected = state.soundType,
                                onSelect = { viewModel.setSound(it) },
                                onPreview = { viewModel.previewSound() }
                            )
                        }
                    }
                }
            }
        }
    }
}

// 🧠 Helper
fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%d:%02d".format(minutes, seconds)
}