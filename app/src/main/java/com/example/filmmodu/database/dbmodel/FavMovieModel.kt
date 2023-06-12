package com.example.filmmodu.database.dbmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "favoriDatabase")
data class FavMovieModel(
    @PrimaryKey
    var movie_imdb_id:String,
    var movie_name:String,
    var movie_genres:List<String>,
    var movie_year:Int,
    var movie_iframe:String,
    var movie_poster:String,
    var movie_description: String
): Serializable