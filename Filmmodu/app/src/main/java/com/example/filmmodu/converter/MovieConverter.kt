package com.example.filmmodu.converter

import com.example.filmmodu.database.dbmodel.FavMovieModel
import com.example.filmmodu.service.model.MovieModel

object MovieConverter {

    fun movieToFavMovie(movie:MovieModel):FavMovieModel{
        return FavMovieModel(
            movie_imdb_id = movie.movie_imdb_id,
            movie_iframe = movie.movie_iframe,
            movie_year = movie.movie_year,
            movie_poster = movie.movie_poster,
            movie_description = movie.movie_description,
            movie_genres = movie.movie_genres,
            movie_name = movie.movie_name
        )
    }

    fun favMovieToMovie(movie:FavMovieModel):MovieModel{
        return MovieModel(
            movie_imdb_id = movie.movie_imdb_id,
            movie_iframe = movie.movie_iframe,
            movie_year = movie.movie_year,
            movie_poster = movie.movie_poster,
            movie_description = movie.movie_description,
            movie_genres = movie.movie_genres,
            movie_name = movie.movie_name
        )
    }

}