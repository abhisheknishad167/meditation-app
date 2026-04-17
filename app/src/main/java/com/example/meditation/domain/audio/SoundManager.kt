package com.example.meditation.domain.audio

import android.content.Context
import android.media.MediaPlayer
import com.example.meditation.R

class SoundManager(private val context: Context) {

    private var tickPlayer: MediaPlayer? = null
    private var bellPlayer: MediaPlayer? = null

    private var mediaPlayer: MediaPlayer? = null

    fun playTick() {
        tickPlayer?.release()
        tickPlayer = MediaPlayer.create(context, R.raw.tick)
        tickPlayer?.start()
    }
    fun playSlowBell() {
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer.create(context, R.raw.slowbel)
        mediaPlayer?.start()
    }

    fun playBell() {
        bellPlayer?.release()
        bellPlayer = MediaPlayer.create(context, R.raw.bell)
        bellPlayer?.start()
    }

    fun release() {
        tickPlayer?.release()
        bellPlayer?.release()
    }
}