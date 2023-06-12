package com.example.filmmodu.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.MutableLiveData
import com.example.filmmodu.R
import com.example.filmmodu.databinding.FragmentPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride
import com.google.android.exoplayer2.trackselection.ExoTrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import java.util.*


class PlayerFragment : DialogFragment() {
    private lateinit var binding: FragmentPlayerBinding

    private var exoPlayer: ExoPlayer? = null
    private var playbackPosition = MutableLiveData(0L)
    private var playWhenReady = true

    lateinit var URL:String

    companion object{
        var position = 0L
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPlayerBinding.inflate(layoutInflater)
        val view =binding.root

        val movieIframe = requireArguments().getString("iframe")

        URL = movieIframe!!
        //URL = "https://vod.eu2.cdnrdn.com/VOD1/Movies_ID/RUS_ID/OkiDokiDeneme.mp4/playlist.m3u8"

        preparePlayer()
        return view
    }


    private fun preparePlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()
        exoPlayer?.playWhenReady = true
        binding.styledPlayerView.player = exoPlayer
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaItem = MediaItem.fromUri(URL)
        val mediaSource =
            HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)

        exoPlayer?.apply {
            setMediaSource(mediaSource)
            seekTo(playbackPosition.value!!)
            playWhenReady = playWhenReady
            prepare()
        }
    }

    private fun releasePlayer() {
        exoPlayer?.let { player ->
            playbackPosition.value = player.currentPosition
            playWhenReady = player.playWhenReady
            player.release()
            exoPlayer = null
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("POSITION resume", playbackPosition.value!!.toString())
        //Toast.makeText(requireContext(),"onResume"+playbackPosition,Toast.LENGTH_SHORT).show()
//        exoPlayer!!.seekTo(0,playbackPosition.value!!);

    }
    override fun onStop() {
        super.onStop()
        //releasePlayer()
        Log.e("POSITION stop", playbackPosition.value!!.toString())
        //Toast.makeText(requireContext(),"onStop"+playbackPosition.toString(),Toast.LENGTH_SHORT).show()
    }
    override fun onPause() {
        super.onPause()
//        releasePlayer(
        playbackPosition.value = exoPlayer!!.currentPosition
        position = exoPlayer!!.currentPosition
        Log.e("POSITION pause", playbackPosition.value!!.toString())
        Toast.makeText(requireContext(),"onPause"+playbackPosition.toString(),Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }



}