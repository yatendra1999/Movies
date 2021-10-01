package com.mml.movies.network.models


import com.fasterxml.jackson.annotation.JsonProperty
import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
@Keep
data class ApiResult(
    @JsonProperty("page")
    val page: Int,
    @JsonProperty("results")
    val results: List<Movies>,
    @JsonProperty("total_pages")
    val totalPages: Int,
    @JsonProperty("total_results")
    val totalResults: Int
)