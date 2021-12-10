package com.example.architecture.repository

import com.example.architecture.api.MovieService

class MovieRepository constructor(private val service: MovieService) {
    suspend fun getAllMovie() = service.getAllMovies()
}