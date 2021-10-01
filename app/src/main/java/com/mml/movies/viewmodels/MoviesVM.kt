package com.mml.movies.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mml.movies.room.MovieDao
import com.mml.movies.room.Stats
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MoviesVM @Inject constructor(
    val movieDao: MovieDao,
) : ViewModel() {

    var bookmarked = movieDao.getBookmarked()

    var allMovies = movieDao.fetchAllMovies()

    fun addBookmark(id: Int){
        movieDao.addBookMark(id).subscribeOn(Schedulers.io()).subscribe()
    }

    fun removeBookmark(id: Int){
        movieDao.removeBookMark(id).subscribeOn(Schedulers.io()).subscribe()
    }
}
