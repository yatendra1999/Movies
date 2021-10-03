package com.mml.movies.repo;

import android.util.Log;
import com.mml.movies.network.ApiService;
import com.mml.movies.network.Constants;
import com.mml.movies.network.models.Movies;
import com.mml.movies.room.MovieDao;
import com.mml.movies.room.Stats;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;

public class TrendingRepo {
    @Inject TrendingRepo(
            MovieDao movieDao,
            ApiService apiService
    ) {
        this.movieDao = movieDao;
        this.apiService = apiService;
    }

    MovieDao movieDao;
    ApiService apiService;

    public Observable<List<Movies>> getTrending(){
        return movieDao.getTrending();
    }

    public void updateData(int page, int max){
        if(page>max){
            return;
        }
        HashMap<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("api_key", Constants.api_key);
        queryMap.put("page", String.valueOf(page));
        apiService.getTrending(
                queryMap
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (it) -> {
                            movieDao.insertMovies(it.results.toArray(new Movies[0])).subscribeOn(Schedulers.io()).subscribe();
                            updateStats(it.results);
                            if(page<it.totalPages){
                                updateData(page+1, it.totalPages);
                            }
                        },
                        (err) ->
                        {
                            err.printStackTrace();
                            Log.e("NetworkInterface", String.format("updateFromAPI: Error at page: %s", String.valueOf(page)));
                        }
                );
    }

    void updateStats(List<Movies> movies){
        for(int i=0; i<movies.size(); i++){
            Movies it = movies.get(i);
            movieDao.fetchSingleStat(it.id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (stat) -> {if(!stat.active) movieDao.setActive(it.id).subscribeOn(Schedulers.io()).subscribe();},
                            (err) -> {movieDao.insertStats(new Stats(it.id, false, true, false)).subscribeOn(Schedulers.io()).subscribe();}
                    );
        }
    }
}