package com.digi.digiapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.exxoplayer.*

class ExxoPlayerFragment : Fragment() {

    private var mPlayer: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    companion object {
        const val NAME_KEY = "nameKey"
        fun newInstance(name: String): ExxoPlayerFragment {
            val fr = ExxoPlayerFragment()
            if (name != null) {
                val args = Bundle()
                args.putString(NAME_KEY, name)
                fr.arguments = args
            }
            return fr
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.exxoplayer, container, false)
        playerView = view.findViewById(R.id.playerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvfilmname.text = arguments?.getString(NAME_KEY)
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initPlayer() {
        mPlayer = SimpleExoPlayer.Builder(requireActivity()).build()
        // Bind the player to the view.
        playerView.player = mPlayer
        mPlayer!!.playWhenReady = true
        mPlayer!!.seekTo(playbackPosition)
        mPlayer!!.prepare(buildMediaSource(), false, false)

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || mPlayer == null) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }


    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        playWhenReady = mPlayer!!.playWhenReady
        playbackPosition = mPlayer!!.currentPosition
        currentWindow = mPlayer!!.currentWindowIndex
        mPlayer!!.release()
        mPlayer = null
    }

    private fun buildMediaSource(): MediaSource {
        val userAgent =
            Util.getUserAgent(playerView.context, arguments?.getString(NAME_KEY)!!)

        val dataSourceFactory = DefaultHttpDataSourceFactory(userAgent)
        val hlsMediaSource =
            HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"))

        return hlsMediaSource
    }

}