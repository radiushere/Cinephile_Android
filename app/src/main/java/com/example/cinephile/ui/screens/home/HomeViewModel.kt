package com.example.cinephile.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinephile.data.remote.TmdbApi
import com.example.cinephile.data.remote.dto.MovieDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: TmdbApi, private val apiKey: String) : ViewModel() {

    val heroBannerItems = mutableStateOf<List<MovieDto>>(emptyList())
    val trendingItems = mutableStateOf<List<MovieDto>>(emptyList())
    val topRatedMovies = mutableStateOf<List<MovieDto>>(emptyList())
    val popularTvShows = mutableStateOf<List<MovieDto>>(emptyList())
    val isLoading = mutableStateOf(true)

    init {
        fetchAll()
    }

    private fun fetchAll() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                heroBannerItems.value = api.getNowPlayingMovies(apiKey).results
                trendingItems.value = api.getTrendingAll(apiKey).results.filter { it.mediaType == "movie" || it.mediaType == "tv" }
                topRatedMovies.value = api.getTopRatedMovies(apiKey).results
                popularTvShows.value = api.getPopularTvShows(apiKey).results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}