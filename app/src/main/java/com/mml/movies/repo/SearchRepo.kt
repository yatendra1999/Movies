package com.mml.movies.repo

import com.mml.movies.network.ApiService
import com.mml.movies.room.MovieDao
import javax.inject.Inject

class SearchRepo {
    @Inject
    lateinit var movieDao: MovieDao
    @Inject
    lateinit var apiService: ApiService
}