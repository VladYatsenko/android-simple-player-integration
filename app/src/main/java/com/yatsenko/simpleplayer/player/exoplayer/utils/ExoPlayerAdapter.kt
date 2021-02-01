package com.yatsenko.simpleplayer.player.exoplayer.utils

import com.google.android.exoplayer2.ExoPlayer
import com.yatsenko.simpleplayer.player.abstraction.PlayerAdapter

interface ExoPlayerAdapter: PlayerAdapter {

    fun getPlayer(): ExoPlayer

}