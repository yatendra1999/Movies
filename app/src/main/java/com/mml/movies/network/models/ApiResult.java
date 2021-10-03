package com.mml.movies.network.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import androidx.annotation.Keep;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Keep
public class ApiResult {
    @JsonProperty("page")
    public int page;
    @JsonProperty("results")
    public List<Movies> results;
    @JsonProperty("total_pages")
    public int totalPages;
    @JsonProperty("total_results")
    public int totalResults;
}