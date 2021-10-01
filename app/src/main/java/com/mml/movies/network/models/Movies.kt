package com.mml.movies.network.models


import com.fasterxml.jackson.annotation.JsonProperty
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class Movies(
    @JsonProperty("adult")
    val adult: Boolean,
    @JsonProperty("backdrop_path")
    val backdropPath: String?,

//    @Ignore
//    @JsonProperty("genre_ids")
//    val genreIds: List<Int>,

    @PrimaryKey
    @JsonProperty("id")
    val id: Int,
    @JsonProperty("original_language")
    val originalLanguage: String,
    @JsonProperty("original_title")
    val originalTitle: String,
    @JsonProperty("overview")
    val overview: String,
    @JsonProperty("popularity")
    val popularity: Double,
    @JsonProperty("poster_path")
    val posterPath: String?,
    @JsonProperty("release_date")
    val releaseDate: String?,
    @JsonProperty("title")
    val title: String,
    @JsonProperty("video")
    val video: Boolean,
    @JsonProperty("vote_average")
    val voteAverage: Double,
    @JsonProperty("vote_count")
    val voteCount: Int
)