package com.example.filmmodu.module

import com.example.filmmodu.annotation.api.OkHttp
import com.example.filmmodu.annotation.api.RetrofitNoAuth
import com.example.filmmodu.annotation.api.ApiNoAuth
import com.example.filmmodu.service.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private val BASE_URL = "https://raw.githubusercontent.com"



    @Provides
    @Singleton
    @ApiNoAuth
    fun provideNoAuthApi(@RetrofitNoAuth retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }


    @Provides
    @Singleton
    @RetrofitNoAuth
    fun provideRetrofit(@OkHttp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }


    @Provides
    @Singleton
    @OkHttp
    fun provideOkHttp(
        builder: OkHttpClient.Builder,
    ): OkHttpClient = builder.build()


    @Provides
    @Singleton
    fun setLogLevel(): HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.NONE


    @Provides
    @Singleton
    fun setupOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .also {
                        it.level = setLogLevel()
                    })
            .followRedirects(true)
            .followSslRedirects(true)
            .readTimeout(timeout = 60, TimeUnit.SECONDS)
            .connectTimeout(timeout = 60, TimeUnit.SECONDS)
            .writeTimeout(timeout = 60, TimeUnit.SECONDS)
    }

}