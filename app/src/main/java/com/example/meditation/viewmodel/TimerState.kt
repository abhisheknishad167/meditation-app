package com.example.meditation.viewmodel

data class TimerState(
    val totalTime: Long = 300_000L,
    val remainingTime: Long = 300_000L,
    val isRunning: Boolean = false,
    val selectedMinutes: Int = 5,
    val isCompleted: Boolean = false,
    val soundType: SoundType = SoundType.TICK // 👈 ADD THIS
) {
    val progress: Float
        get() = remainingTime.toFloat() / totalTime
}

enum class SoundType {
    TICK, BELL
}