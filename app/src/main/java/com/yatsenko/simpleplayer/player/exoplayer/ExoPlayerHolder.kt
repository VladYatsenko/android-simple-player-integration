package com.yatsenko.simpleplayer.player.exoplayer

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import androidx.media.AudioAttributesCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.yatsenko.simpleplayer.player.abstraction.PlayerAdapter
import com.yatsenko.simpleplayer.player.google.AudioFocusWrapper

class ExoPlayerHolder constructor(context: Context): PlayerAdapter {

    val audioFocusPlayer: ExoPlayer

    init {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val audioAttributes = AudioAttributesCompat.Builder()
            .setContentType(AudioAttributesCompat.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributesCompat.USAGE_MEDIA)
            .build()
        audioFocusPlayer = AudioFocusWrapper(audioAttributes, audioManager, ExoPlayerFactory.newSimpleInstance(context, DefaultTrackSelector())
                .also { playerView.player = it }
        )
    }

    override fun loadMedia(uri: Uri) {

    }

    override fun release() {

    }

    override fun isPlaying(): Boolean {

    }

    override fun play() {

    }

    override fun reset() {

    }

    override fun pause() {

    }

    override fun initializeProgressCallback() {

    }

    override fun seekTo(position: Int) {

    }

}