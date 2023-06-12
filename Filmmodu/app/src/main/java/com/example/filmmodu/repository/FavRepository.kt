package com.example.filmmodu.repository

import com.example.filmmodu.database.FavMovieDao
import com.example.filmmodu.database.dbmodel.FavMovieModel
import com.example.filmmodu.service.model.MovieModel
import javax.inject.Inject

class FavRepository@Inject constructor(
    val favMovieDao: FavMovieDao
) {
    suspend fun getFavMovieList():List<FavMovieModel>{
        return favMovieDao.getAllData()
    }

    suspend fun addFavMovie(movie:FavMovieModel){
        favMovieDao.insertData(movie)
    }

    suspend fun deleteFavMovie(movie:FavMovieModel){
        favMovieDao.deleteData(movie.movie_imdb_id)
    }
}