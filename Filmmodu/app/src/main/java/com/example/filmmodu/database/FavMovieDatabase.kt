package com.example.filmmodu.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exampla.redlauncher.converter.MyCustomClassConverter
import com.example.filmmodu.database.dbmodel.FavMovieModel


@Database(entities = [FavMovieModel::class], version = 1)
@TypeConverters(MyCustomClassConverter::class)
abstract class FavMovieDatabase: RoomDatabase() {
    abstract fun dataDao(): FavMovieDao
}