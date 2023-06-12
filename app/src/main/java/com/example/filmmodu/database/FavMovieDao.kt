package com.example.filmmodu.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmmodu.database.dbmodel.FavMovieModel


@Dao
interface FavMovieDao {
    @Query("SELECT * FROM favoriDatabase")
    suspend fun getAllData(): List<FavMovieModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: FavMovieModel)

    @Query("DELETE FROM favoriDatabase WHERE movie_imdb_id = :imdb_id")
    suspend fun deleteData(imdb_id: String)
}