package com.example.filmmodu.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.filmmodu.database.FavMovieDao
import com.example.filmmodu.database.FavMovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent ::class)
@Module
class FavDatabaseModule {
    @Singleton
    @Provides
    fun provideFavMovieDb(app: Application): FavMovieDatabase {
        return Room
            .databaseBuilder(app, FavMovieDatabase::class.java, "favoriDatabase.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun context(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideFavMovieDao(database : FavMovieDatabase): FavMovieDao {
        return database.dataDao()
    }
}