package com.yatsenko.simpleplayer.player.mediaplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.View
import com.yatsenko.simpleplayer.player.abstraction.PlaybackInfoListener
import com.yatsenko.simpleplayer.player.abstraction.PlayerAdapter
import java.util.concurrent.TimeUnit

class MediaPlayerHolder constructor(val context: Context) : PlayerAdapter {

    private var mediaPlayer: MediaPlayer? = null
    private var playbackInfoListener: PlaybackInfoListener? = null

    private fun initializeMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setOnCompletionListener {
                stopUpdatingCallbackWithPosition(true)
                playbackInfoListener?.onStateChanged(PlaybackInfoListener.State.COMPLETED)
                playbackInfoListener?.onPlaybackCompleted()
            }
        }
    }

    fun setPlaybackInfoListener(listener: PlaybackInfoListener) {
        playbackInfoListener = listener
    }

    override fun loadMedia(uri: Uri) {

        initializeMediaPlayer()

        try {
            mediaPlayer?.setDataSource(context, uri)
        } catch (e: Exception) {
//            Logger.crashlyticsLog(e)
        }

        try {
            mediaPlayer?.prepare()
        } catch (e: Exception) {
            Log.i("mMediaPlayer", e.localizedMessage ?: "")
//            Logger.crashlyticsLog(e)
        }

        initializeProgressCallback()
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun isPlaying(): Boolean {
        return if (mediaPlayer != null) mediaPlayer?.isPlaying == true else false
    }

    override fun play() {
        if (mediaPlayer != null && mediaPlayer?.isPlaying != true) {
            mediaPlayer?.start()
            if (playbackInfoListener != null) {
                playbackInfoListener?.onStateChanged(PlaybackInfoListener.State.PLAYING)
            }
            startUpdatingCallbackWithPosition()
        }
    }

    override fun reset() {
        if (mediaPlayer != null) {
            mediaPlayer?.reset()

            playbackInfoListener?.onStateChanged(PlaybackInfoListener.State.RESET)

            stopUpdatingCallbackWithPosition(true)
        }
    }

    override fun pause() {
        if (mediaPlayer != null && mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
            if (playbackInfoListener != null) {
                playbackInfoListener?.onStateChanged(PlaybackInfoListener.State.PAUSED)
            }
        }
    }

    override fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
    }

    private fun startUpdatingCallbackWithPosition() {
        compositeDisposable = Observable.interval(10L, TimeUnit.MILLISECONDS)
            .timeInterval()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { updateProgressCallbackTask() }
    }

    private fun stopUpdatingCallbackWithPosition(resetUIPlaybackPosition: Boolean) {
        compositeDisposable.dispose()
    }

    private fun updateProgressCallbackTask() {
        if (mediaPlayer?.isPlaying == true) {
            val currentPosition = mediaPlayer?.currentPosition ?: 0
            playbackInfoListener?.onPositionChanged(currentPosition)
        }
    }

    override fun initializeProgressCallback() {
        val duration = mediaPlayer?.duration ?: 0
        playbackInfoListener?.onDurationChanged(duration)
        playbackInfoListener?.onPositionChanged(0)
    }

}