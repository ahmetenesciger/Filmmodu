package com.example.filmmodu.repository

import android.util.Log
import com.example.filmmodu.annotation.api.ApiNoAuth
import com.example.filmmodu.service.model.MovieModel
import com.example.filmmodu.service.MovieApi
import com.example.filmmodu.service.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class HomeRepository @Inject constructor(
    @ApiNoAuth val api: MovieApi
) {

    fun getMovies(): Flow<Resource<List<MovieModel>>> {
        return flow {
            emit(Resource.loading(null))

            val countryData = api.getMovieList()
            Log.e("deneme 1","Succes oldu")

            emit(Resource.success(countryData))
        }.catch {
            Log.e("deneme 2","Error oldu")
            emit(Resource.error(it.message!!, null, it))

        }.flowOn(Dispatchers.IO)

    }

}