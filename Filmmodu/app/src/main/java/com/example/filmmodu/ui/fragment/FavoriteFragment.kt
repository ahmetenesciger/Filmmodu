package com.example.filmmodu.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmmodu.R
import com.example.filmmodu.adapter.RecyclerViewAdapter
import com.example.filmmodu.database.dbmodel.FavMovieModel
import com.example.filmmodu.databinding.FragmentFavoriteBinding
import com.example.filmmodu.databinding.FragmentHomeBinding
import com.example.filmmodu.exstension.navigateSafe
import com.example.filmmodu.service.data.Status
import com.example.filmmodu.service.model.MovieModel
import com.example.filmmodu.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    private val blankViewModel: HomeViewModel by viewModels()
    lateinit var movieList:List<MovieModel>
    lateinit var homeRecyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        var view = binding.root

        initMovieList()

        return view
    }

    private val itemClick: (MovieModel) -> Unit =  {
            movieItem -> navigateSafe(R.id.action_FavoriteFragment_to_movieDetailFragment, bundleOf("movie" to movieItem)
    )}

    private fun initMovieList() {
        lifecycleScope.launch {
            blankViewModel.getFavMovies().let {
                var dummyMovieList=convertFavMovieToMovie(it)
                movieList=dummyMovieList
                binding.rvFavoriteFragment.layoutManager = GridLayoutManager(requireContext(), 2)
                homeRecyclerViewAdapter=RecyclerViewAdapter(itemClick, movieList,requireContext())
                binding.rvFavoriteFragment.adapter=homeRecyclerViewAdapter
            }
        }

    }


    fun convertFavMovieToMovie(favMovieList:List<FavMovieModel>):List<MovieModel>{
        var dummyList:MutableList<MovieModel> = mutableListOf()

        for (favMovie in favMovieList){
            var x=MovieModel(movie_imdb_id = favMovie.movie_imdb_id,
                movie_name = favMovie.movie_name,
                movie_genres = favMovie.movie_genres,
                movie_description = favMovie.movie_description,
                movie_poster = favMovie.movie_poster,
                movie_year = favMovie.movie_year,
                movie_iframe = favMovie.movie_iframe
            )
            dummyList.add(x)
        }
        return dummyList
    }


}
