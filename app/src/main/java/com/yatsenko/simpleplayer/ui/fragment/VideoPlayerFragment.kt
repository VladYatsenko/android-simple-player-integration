package com.yatsenko.simpleplayer.ui.fragment

import android.media.AudioManager
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.yatsenko.simpleplayer.R
import com.yatsenko.simpleplayer.model.Resource
import com.yatsenko.simpleplayer.player.exoplayer.ExoPlayerHolder
import com.yatsenko.simpleplayer.player.exoplayer.utils.ExoPlayerAdapter
import kotlinx.android.synthetic.main.fragment_video_player.*

class VideoPlayerFragment : Fragment() {

    private var playerAdapter: ExoPlayerAdapter? = null

    private val mediaSession: MediaSessionCompat by lazy { createMediaSession() }
    private val mediaSessionConnector: MediaSessionConnector by lazy { createMediaSessionConnector() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_video_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPlayer()

        val resource = Resource("0", "video", "Title", "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
        playerAdapter?.loadMedia(resource)
    }

    override fun onStart() {
        super.onStart()
        playerAdapter?.play()

        //activateMediaSession
        mediaSessionConnector.setPlayer(playerAdapter?.getPlayer())
        mediaSession.isActive = true
    }

    override fun onStop() {
        super.onStop()
        playerAdapter?.stop()

        //deactivateMediaSession
        mediaSessionConnector.setPlayer(null)
        mediaSession.isActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        playerAdapter?.release()

        mediaSession.release()
    }

    private fun setupPlayer() {
        // While the user is in the app, the volume controls should adjust the music volume.
        activity?.volumeControlStream = AudioManager.STREAM_MUSIC
        createMediaSession()
        val mediaPlayerHolder = ExoPlayerHolder(requireContext())
        playerAdapter = mediaPlayerHolder
        exoplayerView.player = playerAdapter?.getPlayer()
    }

    // MediaSession related functions.
    private fun createMediaSession(): MediaSessionCompat = MediaSessionCompat(requireContext(), requireContext().packageName)

    private fun createMediaSessionConnector(): MediaSessionConnector {
        // If QueueNavigator isn't set, then mediaSessionConnector will not handle following
        // MediaSession actions (and they won't show up in the minimized PIP activity):
        // [ACTION_SKIP_PREVIOUS], [ACTION_SKIP_NEXT], [ACTION_SKIP_TO_QUEUE_ITEM]
//        mediaSessionConnector.setQueueNavigator(object : TimelineQueueNavigator(mediaSession) {
//            override fun getMediaDescription(player: Player, windowIndex: Int): MediaDescriptionCompat {
//                return mediaCatalog[windowIndex]
//            }
//        })
        return MediaSessionConnector(mediaSession)

    }

    fun useController(use: Boolean) {
        exoplayerView.useController = use
    }

}