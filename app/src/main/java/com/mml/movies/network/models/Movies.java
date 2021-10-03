package com.mml.movies.network.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Movies {
    @JsonProperty("adult")
    public boolean adult;

    @JsonProperty("backdrop_path")
    public String backdropPath;

    @PrimaryKey
    @JsonProperty("id")
    public int id;

    @JsonProperty("original_language")
    public String originalLanguage;

    @JsonProperty("original_title")
    public String originalTitle;

    @JsonProperty("overview")
    public String overview;

    @JsonProperty("popularity")
    public double popularity;

    @JsonProperty("poster_path")
    public String posterPath;

    @JsonProperty("release_date")
    public String releaseDate;

    @JsonProperty("title")
    public String title;

    @JsonProperty("video")
    public boolean video;

    @JsonProperty("vote_average")
    public double voteAverage;

    @JsonProperty("vote_count")
    public int voteCount;
}