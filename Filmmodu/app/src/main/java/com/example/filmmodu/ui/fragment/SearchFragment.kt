package com.example.filmmodu.ui.fragment





import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.filmmodu.R
import com.example.filmmodu.adapter.RecyclerViewAdapter
import com.example.filmmodu.databinding.FragmentSearchBinding
import com.example.filmmodu.databinding.MovieListItemBinding
import com.example.filmmodu.exstension.navigateSafe
import com.example.filmmodu.helper.DebouncingQueryTextListener
import com.example.filmmodu.service.data.Status
import com.example.filmmodu.service.model.MovieModel
import com.example.filmmodu.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var itemBinding: MovieListItemBinding
    private val blankViewModel: HomeViewModel by viewModels()
    lateinit var movieList:List<MovieModel>
    lateinit var homeRecyclerViewAdapter: RecyclerViewAdapter
    lateinit var searchView: SearchView
    private lateinit var courseModelArrayList: ArrayList<MovieModel>


    private val itemClick: (MovieModel) -> Unit =  {
            movieItem -> navigateSafe(R.id.action_SearchFragment_to_movieDetailFragment, bundleOf("movie" to movieItem)
    )}



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        itemBinding = MovieListItemBinding.inflate(layoutInflater)
        val view =binding.root
        initMovieList()
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchMovieText.requestFocus()




        binding.searchMovieText.addTextChangedListener(DebouncingQueryTextListener(lifecycle) { query ->
            lifecycleScope.launch {
                val filteredData = filterMovieList(query.toString())
                homeRecyclerViewAdapter.updateData(filteredData)
                homeRecyclerViewAdapter.notifyDataSetChanged()
            }
        })


    }


    private fun filterMovieList(query: String): List<MovieModel> {
        val filteredList = ArrayList<MovieModel>()

        for (movie in movieList) {
            if (movie.movie_name.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))) {
                filteredList.add(movie)
            }
        }

        return filteredList
    }


    private fun initMovieList() {

        lifecycleScope.launch {
            blankViewModel.getMoviesData().collect {
                when (it.status) {
                    Status.SUCCESS -> {

                        Log.e("deneme33", it.data?.size.toString())
                        movieList= it.data!!

                        binding.rvSearchFragment.layoutManager = GridLayoutManager(requireContext(), 2)
                        homeRecyclerViewAdapter=RecyclerViewAdapter(itemClick, movieList,requireContext())
                        binding.rvSearchFragment.adapter=homeRecyclerViewAdapter

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