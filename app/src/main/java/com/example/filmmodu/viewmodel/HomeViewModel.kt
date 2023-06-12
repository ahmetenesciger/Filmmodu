package com.example.filmmodu.viewmodel

import androidx.lifecycle.ViewModel
import com.example.filmmodu.converter.MovieConverter
import com.example.filmmodu.database.dbmodel.FavMovieModel
import com.example.filmmodu.repository.FavRepository
import com.example.filmmodu.repository.HomeRepository
import com.example.filmmodu.service.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val homeRepository:HomeRepository,
    val favRepository:FavRepository):ViewModel() {

    fun getMoviesData() = homeRepository.getMovies()

    suspend fun getFavMovies() = favRepository.getFavMovieList()


    suspend fun checkMovieInDatabase(movie:MovieModel):Boolean{
        val favMoviList = getFavMovies()
        var dummy=false
        for (favMovie in favMoviList){
            if (favMovie.movie_imdb_id == movie.movie_imdb_id)
                dummy = true
        }
        return dummy
    }

    suspend fun deleteFavMovie(favMovie:MovieModel){
        var dummy=MovieConverter.movieToFavMovie(favMovie)

        var isFav=checkMovieInDatabase(favMovie)

        if (isFav == true){
            favRepository.deleteFavMovie(movie = dummy)
        }
    }

    suspend fun addFavMovie(favMovie:MovieModel){
        var dummy=MovieConverter.movieToFavMovie(favMovie)

        var isFav=checkMovieInDatabase(favMovie)

        if (isFav == false){
            favRepository.addFavMovie(movie = dummy)
        }
    }

}