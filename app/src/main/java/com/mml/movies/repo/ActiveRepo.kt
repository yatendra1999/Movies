package com.mml.movies.repo

import android.util.Log
import com.mml.movies.network.ApiService
import com.mml.movies.network.Constants
import com.mml.movies.network.models.ApiResult
import com.mml.movies.network.models.Movies
import com.mml.movies.room.MovieDao
import com.mml.movies.room.Stats
import dagger.hilt.InstallIn
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ActiveRepo @Inject constructor(
    var movieDao: MovieDao,
    var apiService: ApiService
) {

    var lastUpdate: Long = 0

    fun getActive(): Observable<List<Movies>> {
        return movieDao.getActive()
    }

    fun updateData(page: Int = 1, max: Int = 1){
        if(System.currentTimeMillis() <= lastUpdate + 3600000 || page>max){
            return
        }
        apiService.getNowPlaying(
            mapOf<String, String>(
                "api_key" to Constants.api_key,
                "page" to page.toString()
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    movieDao.insertMovies(*it.results.toTypedArray()).subscribeOn(Schedulers.io()).subscribe()
                    updateStats(it.results)
                    Log.d("NetworkInterface", "updateFromAPI: Fetched $page/${it.totalPages} Now Playing pages")
                    if(page<it.totalPages){
                        updateData(page+1, it.totalPages)
                    }
                    else{
                        lastUpdate = System.currentTimeMillis()
                    }
                },
                {
                    Log.e("NetworkInterface", "updateFromAPI: Error ${it.printStackTrace()} at page: $page")
                }
            )
    }

    private fun updateStats(movies: List<Movies>){
        movies.forEach{
            movieDao.fetchSingleStat(it.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {stat:Stats -> if(!stat.active) movieDao.setActive(it.id).subscribeOn(Schedulers.io()).subscribe() },
                    { _ -> movieDao.insertStats(Stats(it.id, false, false, true)).subscribeOn(Schedulers.io()).subscribe() }
                )
        }
    }
}