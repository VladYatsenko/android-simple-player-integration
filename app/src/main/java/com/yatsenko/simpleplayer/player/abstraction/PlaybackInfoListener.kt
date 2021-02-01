package com.yatsenko.simpleplayer.player.abstraction

abstract class PlaybackInfoListener {

    enum class State {INVALID, PLAYING, PAUSED, RESET, COMPLETED }

    fun convertStateToString(state: State): String {
        val stateString: String
        when (state) {
            State.COMPLETED -> stateString = "COMPLETED"
            State.INVALID -> stateString = "INVALID"
            State.PAUSED -> stateString = "PAUSED"
            State.PLAYING -> stateString = "PLAYING"
            State.RESET -> stateString = "RESET"
            else -> stateString = "N/A"
        }
        return stateString
    }

    internal open fun onDurationChanged(duration: Int) {}

    internal open fun onPositionChanged(position: Int) {}

    internal open fun onStateChanged(state: State) {}

    internal open fun onPlaybackCompleted() {}

}