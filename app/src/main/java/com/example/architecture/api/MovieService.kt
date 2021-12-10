package com.example.architecture.api

import com.example.architecture.model.Movie
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MovieService {
    @GET("movielist.json")
    suspend fun getAllMovies() : Response<List<Movie>>

    companion object {
        var retrofitService: MovieService? = null
        fun getInstance() : MovieService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(MovieService::class.java)
            }
            return retrofitService!!
        }

    }
}