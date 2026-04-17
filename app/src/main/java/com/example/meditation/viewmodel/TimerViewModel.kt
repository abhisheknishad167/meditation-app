package com.example.meditation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meditation.domain.audio.SoundManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val _state = MutableStateFlow(TimerState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    // ✅ MOVE INSIDE CLASS
    private var soundManager: SoundManager? = null

    fun setSoundManager(manager: SoundManager) {
        soundManager = manager
    }

    fun toggleTimer() {
        if (_state.value.isRunning) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        job?.cancel()

        _state.update { it.copy(isRunning = true) }

        job = viewModelScope.launch {
               // 👈 track elapsed time

            var elapsed = 0L   // 👈 ADD this ABOVE while loop

            while (_state.value.remainingTime > 0) {

                delay(1000)
                elapsed += 1000

                when (_state.value.soundType) {

                    SoundType.TICK -> {
                        soundManager?.playTick()
                    }

                    SoundType.BELL -> {
                        if (elapsed % 15000L == 0L) {   // 👈 every 15 sec
                            soundManager?.playSlowBell()
                        }
                    }
                }

                _state.update {
                    it.copy(
                        remainingTime = it.remainingTime - 1000
                    )
                }
            }
            // 🔔 PLAY BELL WHEN TIMER FINISHES
            soundManager?.playBell()

            _state.update {
                it.copy(
                    isRunning = false,
                    isCompleted = true
                )
            }
        }
    }

    fun setSound(type: SoundType) {
        _state.update { it.copy(soundType = type) }
    }

    fun previewSound() {
        when (_state.value.soundType) {
            SoundType.TICK -> soundManager?.playTick()
            SoundType.BELL -> soundManager?.playSlowBell()
        }
    }

    private fun pauseTimer() {
        job?.cancel()
        _state.update { it.copy(isRunning = false) }
    }

    fun reset() {
        job?.cancel()
        _state.update {
            it.copy(
                remainingTime = it.totalTime,
                isRunning = false,
                isCompleted = false
            )
        }
    }

    fun setDuration(minutes: Int) {
        val millis = minutes * 60 * 1000L

        job?.cancel()
        _state.update {
            it.copy(
                totalTime = millis,
                remainingTime = millis,
                selectedMinutes = minutes,
                isRunning = false,
                isCompleted = false
            )
        }
    }
}