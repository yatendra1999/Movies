package com.mml.movies.repo

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mml.movies.network.ApiService
import com.mml.movies.network.Constants
import com.mml.movies.network.models.Movies
import com.mml.movies.room.MovieDao
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SearchRepo @Inject constructor(
    var movieDao: MovieDao,
    var apiService: ApiService){
    var searchResults: Observable<List<Movies>> = movieDao.fetchAllMovies()
    var handler = Handler(Looper.getMainLooper())

    lateinit var searchTask: Runnable

    fun onSearch(query: String){
        if(query.isEmpty()){
            handler.removeCallbacksAndMessages(null)
            searchResults = movieDao.fetchAllMovies()
        }
        else{
            handler.removeCallbacksAndMessages(null)
            searchTask = Runnable {
                searchAPI(query)
            }
            searchResults = movieDao.fetchByName("%$query%")
            handler.postDelayed(searchTask, 2000)
        }
    }

    fun searchAPI(query: String){
        apiService.getSearchResult(
            mapOf(
                "api_key" to Constants.api_key,
                "query" to query
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {response -> addToDB(response.results); Log.d("SearchResults", "Got ${response.results.size} movies matching the Search String $query")},
                {}
            )
    }

    fun addToDB(movies: List<Movies>){
        movieDao.insertMovies(*movies.toTypedArray())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}