package com.yatsenko.simpleplayer.player.abstraction

import android.net.Uri
import android.view.View

interface PlayerAdapter {

//    var playerView: T

    fun loadMedia(uri: Uri)

    fun release()

    fun isPlaying(): Boolean

    fun play()

    fun reset()

    fun pause()

    fun initializeProgressCallback()

    fun seekTo(position: Int)

//    fun <T> setPlayerView(view: T)

}