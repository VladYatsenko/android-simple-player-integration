package com.yatsenko.simpleplayer.player.abstraction

import android.view.View
import com.yatsenko.simpleplayer.player.abstraction.model.MediaResource

interface PlayerAdapter {

    // Prepare playback.
    fun loadMedia(resource: MediaResource)

    // Destroy the player instance.
    fun release()

    fun isPlaying(): Boolean

    fun play()

    // Stop playback and release resources, but re-use the player instance.
    fun stop()

    fun pause()

//    fun initializeProgressCallback()

    fun seekTo(position: Int)

}