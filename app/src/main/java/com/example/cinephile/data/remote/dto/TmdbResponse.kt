package com.example.cinephile.data.remote.dto

// This is a generic response class for any TMDB endpoint that returns a list
data class TmdbResponse(
    val results: List<MovieDto>
)