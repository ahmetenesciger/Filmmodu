package com.example.filmmodu.service.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieModel(
    @SerializedName("imdbId")
    var movie_imdb_id:String,
    @SerializedName("name")
    var movie_name:String,
    @SerializedName("genres")
    var movie_genres:List<String>,
    @SerializedName("year")
    var movie_year:Int,
    @SerializedName("iframe")
    var movie_iframe:String,
    @SerializedName("picture")
    var movie_poster:String,
    @SerializedName("description")
    var movie_description:String
): Serializable