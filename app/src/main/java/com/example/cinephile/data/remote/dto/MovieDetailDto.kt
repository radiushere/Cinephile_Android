package com.example.cinephile.data.remote.dto

import com.google.gson.annotations.SerializedName

// This DTO holds ALL details for the detail screen
data class MovieDetailDto(
    val id: Int,
    val title: String?,
    val name: String?, // For TV shows
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    val genres: List<GenreDto>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?, // For TV shows
    val runtime: Int? // For Movies
)

data class GenreDto(
    val id: Int,
    val name: String
)