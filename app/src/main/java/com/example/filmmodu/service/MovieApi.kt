package com.example.filmmodu.service

import com.example.filmmodu.service.model.MovieModel
import retrofit2.http.GET

interface MovieApi{
    @GET("/ahmetenesciger/project-movie-and-series-finder/main/filmmodu_movie_list.json")
    suspend fun getMovieList(): List<MovieModel>


}