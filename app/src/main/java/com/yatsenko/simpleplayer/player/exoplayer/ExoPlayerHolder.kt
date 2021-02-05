package com.yatsenko.simpleplayer.player.exoplayer

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import androidx.media.AudioAttributesCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.yatsenko.simpleplayer.R
import com.yatsenko.simpleplayer.player.abstraction.model.MediaResource
import com.yatsenko.simpleplayer.player.exoplayer.utils.AudioFocusWrapper
import com.yatsenko.simpleplayer.player.exoplayer.utils.ExoPlayerAdapter

class ExoPlayerHolder constructor(private val context: Context) : ExoPlayerAdapter {

    private val audioFocusPlayer: ExoPlayer
    private val trackSelector: DefaultTrackSelector
    private val userAgent: String
    private val bandwidthMeter: DefaultBandwidthMeter

    // Create the player instance.
    init {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val audioAttributes = AudioAttributesCompat.Builder()
            .setContentType(AudioAttributesCompat.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributesCompat.USAGE_MEDIA)
            .build()

        trackSelector = DefaultTrackSelector(context, AdaptiveTrackSelection.Factory())
        userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))
        bandwidthMeter = DefaultBandwidthMeter.Builder(context).build()

        val player = SimpleExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .setLoadControl(DefaultLoadControl())
            .build()
        audioFocusPlayer = AudioFocusWrapper(audioAttributes, audioManager, player)
    }

    override fun loadMedia(resource: MediaResource) {

        try {
            play()
            val currentMediaSource = buildMediaSource(Uri.parse(resource.getMediaResourceSource()))
            audioFocusPlayer.setMediaSource(currentMediaSource)
            audioFocusPlayer.prepare()
            addListener(eventListener)
        } catch (e: Exception){
        }

    }

    override fun release() {
        audioFocusPlayer.release() // player instance can't be used again.
    }

    override fun isPlaying(): Boolean = audioFocusPlayer.playWhenReady

    override fun play() {
        audioFocusPlayer.playWhenReady = true
    }

    override fun stop() {
        audioFocusPlayer.stop(true)
    }

    override fun pause() {
        audioFocusPlayer.playWhenReady = false
    }

    override fun seekTo(position: Int) {
        //todo select right type of position
        audioFocusPlayer.seekTo(position.toLong())
    }

    override fun getPlayer(): ExoPlayer {
        return audioFocusPlayer
    }

    private fun addListener(listener: Player.EventListener) {
        audioFocusPlayer.addListener(listener)
    }

    private val eventListener = object : Player.EventListener {


        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//            info { "playerStateChanged: ${getStateString(playbackState)}, $playWhenReady" }
        }

        override fun onPlayerError(error: ExoPlaybackException) {
//            info { "playerError: $error" }
        }

        fun getStateString(state: Int): String {
            return when (state) {
                Player.STATE_BUFFERING -> "STATE_BUFFERING"
                Player.STATE_ENDED -> "STATE_ENDED"
                Player.STATE_IDLE -> "STATE_IDLE"
                Player.STATE_READY -> "STATE_READY"
                else -> "?"
            }
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val httpDataSourceFactory = DefaultHttpDataSourceFactory(
            userAgent,
            bandwidthMeter,
            DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
            DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
            true
        )
        //     val dataSourceFactory = OkHttpDataSourceFactory(okHttpClient, userAgent, bandwidthMeter)
        val dataSourceFactory = DefaultDataSourceFactory(context, bandwidthMeter, httpDataSourceFactory)
        val mediaItem = MediaItem.fromUri(uri)
        return when (Util.inferContentType(uri)) {
            C.TYPE_SS -> SsMediaSource.Factory(DefaultSsChunkSource.Factory(dataSourceFactory), dataSourceFactory).createMediaSource(mediaItem)
            C.TYPE_DASH -> DashMediaSource.Factory(DefaultDashChunkSource.Factory(dataSourceFactory), dataSourceFactory).createMediaSource(MediaItem.fromUri(uri))
            C.TYPE_HLS -> HlsMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
            C.TYPE_OTHER -> ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)
            else -> throw Exception("Unsupported video type")
        }
    }

}