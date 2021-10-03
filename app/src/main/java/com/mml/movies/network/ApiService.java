package com.mml.movies.network;

import com.mml.movies.network.models.ApiResult;

import java.util.Map;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {
    @GET("movie/popular")
    Single<ApiResult> getTrending(@QueryMap Map<String, String> queries);

    @GET("movie/now_playing")
    Single<ApiResult> getNowPlaying(@QueryMap Map<String, String> queries);

    @GET("search/movie")
    Single<ApiResult> getSearchResult(@QueryMap Map<String, String> queries);
}