package com.yatsenko.simpleplayer.player.exoplayer.model

data class PlayerState(
    var window: Int = 0,
    var position: Long = 0,
    var whenReady: Boolean = true
)