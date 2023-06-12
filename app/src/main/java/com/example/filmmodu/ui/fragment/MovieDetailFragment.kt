package com.example.filmmodu.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.filmmodu.R
import com.example.filmmodu.databinding.FragmentMovieDetailBinding
import com.example.filmmodu.exstension.navigateSafe
import com.example.filmmodu.service.model.MovieModel
import com.example.filmmodu.viewmodel.HomeViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope as scope


@AndroidEntryPoint
class MovieDetailFragment : DialogFragment() {
    private lateinit var binding: FragmentMovieDetailBinding

    private val blankViewModel: HomeViewModel by viewModels()

    //private var isFav=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTheme)
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        val view =binding.root


        val movieData = requireArguments().getSerializable("movie") as MovieModel

        lifecycleScope.launch {

            favIconChanger(movieData)
            binding.movieName.text=movieData.movie_name
            binding.movieDescription.text=movieData.movie_description
            binding.movieYear.text=movieData.movie_year.toString()
            Glide.with(requireContext()).load(movieData.movie_poster).into(binding.imageView)


           // getMetaData()
        }

        binding.playButton.setOnClickListener {
            //Toast.makeText(requireContext(),movieData.movie_imdb_id,Toast.LENGTH_SHORT).show()
            navigateSafe(R.id.action_movieDetailFragment_to_playerFragment, bundleOf("iframe" to movieData.movie_iframe))
        }

        binding.favAddButton.setOnClickListener {
            scope.launch(Dispatchers.IO){
//                blankViewModel.addFavMovie(movieData)
                favStatusChanger(movieData)
                favIconChanger(movieData)
            }
        }

//        val bundle=navArgs<MovieDetailFragmentArgs>()
//        val movieData=bundle.value.selectedMovie
        return view
    }


//    suspend fun getMetaData(){
//
//        val player = SimpleExoPlayer.Builder(requireContext()).build()
//        //val mediaItem: MediaItem = MediaItem.fromUri("https://vod.eu2.cdnrdn.com/VOD1/Movies_ID/RUS_ID/OkiDokiDeneme.mp4/playlist.m3u8")
//        val mediaItem: MediaItem = MediaItem.fromUri()
//        player.setMediaItem(mediaItem)
//        player.prepare();
//        val metadata = player.currentMediaItem?.mediaMetadata
//
//        Log.e("myTrack",metadata?.subtitle.toString())
//
//        player.release();
//    }
    suspend fun getFavStatus(movie:MovieModel):Boolean{
        return blankViewModel.checkMovieInDatabase(movie)
    }

    suspend fun favIconChanger(movie:MovieModel) {
        var isFav=getFavStatus(movie)
        if (isFav == true){
            binding.favAddButton.setImageResource(R.drawable.movie_favicon_red)
            //binding.favAddButton.text="*"
        }
        else{
            binding.favAddButton.setImageResource(R.drawable.movie_favicon)
        }
        Log.e("databaseCheck",isFav.toString())
    }

    suspend fun favStatusChanger(movie:MovieModel){
        var isFav=getFavStatus(movie)
        if (isFav == true){
            blankViewModel.deleteFavMovie(movie)
        }
        else{
            blankViewModel.addFavMovie(movie)
        }
        Log.e("databaseCheckStatus",isFav.toString())
    }

}