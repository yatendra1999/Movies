package com.mml.movies.network.models


import com.fasterxml.jackson.annotation.JsonProperty
import androidx.annotation.Keep

@Keep
data class ApiResult(
    @JsonProperty("page")
    val page: Int,
    @JsonProperty("results")
    val results: List<Movie>,
    @JsonProperty("total_pages")
    val totalPages: Int,
    @JsonProperty("total_results")
    val totalResults: Int
)