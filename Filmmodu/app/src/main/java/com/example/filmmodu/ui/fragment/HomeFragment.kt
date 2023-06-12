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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmmodu.R
import com.example.filmmodu.adapter.RecyclerViewAdapter
import com.example.filmmodu.databinding.FragmentHomeBinding
import com.example.filmmodu.databinding.MovieListItemBinding
import com.example.filmmodu.exstension.navigateSafe
import com.example.filmmodu.service.model.MovieModel
import com.example.filmmodu.service.data.Status
import com.example.filmmodu.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var itemBinding: MovieListItemBinding
    private val blankViewModel: HomeViewModel by viewModels()

    lateinit var movieList:List<MovieModel>
    lateinit var homeRecyclerViewAdapter: RecyclerViewAdapter


    private val itemClick: (MovieModel) -> Unit =  {
            movieItem -> navigateSafe(R.id.action_homeFragment_to_movieDetailFragment, bundleOf("movie" to movieItem)
    )}


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        itemBinding = MovieListItemBinding.inflate(layoutInflater)
        val view =binding.root
        initMovieList()

        lifecycleScope.launch {
            var x=blankViewModel.getFavMovies()
            Log.i("favMovieList",x.toString())
        }

        return view
    }



    private fun initMovieList() {

        lifecycleScope.launch {
            blankViewModel.getMoviesData().collect {
                when (it.status) {
                    Status.SUCCESS -> {

                        Log.e("deneme33", it.data?.size.toString())
                        movieList= it.data!!

                        binding.rvHomeFragment.layoutManager = GridLayoutManager(requireContext(), 2)
                        homeRecyclerViewAdapter=RecyclerViewAdapter(itemClick, movieList,requireContext())
                        binding.rvHomeFragment.adapter=homeRecyclerViewAdapter

                        //binding.rvHomeFragment.scrollToPosition(3)
                        //itemBinding.tvMovieName.movementMethod = ScrollingMovementMethod()





                    }
                    Status.ERROR -> {
                        Log.e("error",it.message.toString())
                    }

                    Status.LOADING -> {
                        Log.e("loading",it.status.toString())
                    }
                }
            }


        }

    }

}