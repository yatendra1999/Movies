package com.mml.movies.repo;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.mml.movies.network.ApiService;
import com.mml.movies.network.Constants;
import com.mml.movies.network.models.Movies;
import com.mml.movies.room.MovieDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;

public class SearchRepo {
    @Inject SearchRepo(
            MovieDao movieDao,
            ApiService apiService){
        this.movieDao = movieDao;
        this.apiService = apiService;
        searchResults = movieDao.fetchAllMovies();
    }

    MovieDao movieDao;
    ApiService apiService;
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable searchTask;
    public Observable<List<Movies>> searchResults;

    public void onSearch(String query){
        handler.removeCallbacksAndMessages(null);
        if(query.isEmpty()){
            searchResults = movieDao.fetchAllMovies();
        }
        else{
            searchTask = () -> searchAPI(query);
            handler.postDelayed(searchTask, 3000);
            searchResults = movieDao.fetchByName("%".concat(query).concat("%"));
        }
    }

    void searchAPI(String query){
        HashMap<String, String> queryMap = new HashMap<String, String>();
        queryMap.put("api_key",Constants.api_key);
        queryMap.put("query", query);
        apiService.getSearchResult(queryMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (response) ->{
                            addToDB(response.results);
                            Log.d("SearchResults", "Got ${response.results.size} movies matching the Search String $query");
                        },
                        (err) -> {}
                );
    }

    void addToDB(List<Movies> movies){
        movieDao.insertMovies(movies.toArray(new Movies[0]))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}