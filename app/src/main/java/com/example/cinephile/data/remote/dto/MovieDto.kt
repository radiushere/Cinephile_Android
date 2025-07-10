package com.example.cinephile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val title: String?, // Movie title
    val name: String?, // TV show name
    @SerializedName("media_type")
    val mediaType: String?
)