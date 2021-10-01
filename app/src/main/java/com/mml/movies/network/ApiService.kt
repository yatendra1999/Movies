package com.mml.movies.network

import com.mml.movies.network.models.ApiResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("movie/popular")
    fun getTrending(@QueryMap queries:Map<String, String>): Single<ApiResult>

    @GET("movie/now_playing")
    fun getNowPlaying(@QueryMap queries:Map<String, String>): Single<ApiResult>

    @GET("search/movie")
    fun getSearchResult(@QueryMap queries:Map<String, String>): Single<ApiResult>
}