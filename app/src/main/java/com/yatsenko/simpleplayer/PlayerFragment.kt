package com.yatsenko.simpleplayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yatsenko.simpleplayer.player.abstraction.PlaybackInfoListener
import com.yatsenko.simpleplayer.player.abstraction.PlayerAdapter
import com.yatsenko.simpleplayer.player.mediaplayer.MediaPlayerHolder

class PlayerFragment: Fragment() {

    private var playerAdapter: PlayerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPlayer()
    }

    private fun setupPlayer() {
        val mediaPlayerHolder = MediaPlayerHolder(requireContext())
        mediaPlayerHolder.setPlaybackInfoListener(PlaybackListener())
        playerAdapter = mediaPlayerHolder
    }



    inner class PlaybackListener : PlaybackInfoListener() {

        override fun onDurationChanged(duration: Int) {
//            playedMessage?.durationMs = duration
        }

        override fun onPositionChanged(position: Int) {
//            playedMessage?.playbackPosition = position
//            update()
        }

        override fun onStateChanged(state: State) {
//            if (state != State.PLAYING && state != State.PAUSED)
//                playedMessage?.playbackPosition = 0
//
//            playedMessage?.voiceState = state
//            updateMessage()
        }

        override fun onPlaybackCompleted() {}

        private fun updateMessage() {
//            messages?.forEachIndexed { index, messageEntity ->
//                if (messageEntity.id == playedMessage?.id) {
//
//                    messages?.set(index, playedMessage!!)
//                    messageAdapter.notifyItemChanged(index)
//                    return@forEachIndexed
//                }
//            }

        }
    }

}