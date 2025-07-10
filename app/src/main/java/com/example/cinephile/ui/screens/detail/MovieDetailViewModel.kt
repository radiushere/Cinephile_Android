package com.example.cinephile.ui.screens.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinephile.data.remote.TmdbApi
import com.example.cinephile.data.remote.dto.CastMemberDto
import com.example.cinephile.data.remote.dto.MovieDetailDto
import com.example.cinephile.data.remote.dto.MovieDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val api: TmdbApi,
    private val apiKey: String,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val details = mutableStateOf<MovieDetailDto?>(null)
    val cast = mutableStateOf<List<CastMemberDto>>(emptyList())
    val similar = mutableStateOf<List<MovieDto>>(emptyList())
    val isLoading = mutableStateOf(true)
    val mediaType = mutableStateOf("movie")

    init {
        val mediaId: Int = checkNotNull(savedStateHandle["mediaId"])
        val type: String = checkNotNull(savedStateHandle["mediaType"])
        mediaType.value = type
        fetchDetails(type, mediaId)
    }

    private fun fetchDetails(type: String, id: Int) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                if (type == "movie") {
                    details.value = api.getMovieDetails(id, apiKey)
                    cast.value = api.getMovieCredits(id, apiKey).cast
                    similar.value = api.getSimilarMovies(id, apiKey).results
                } else {
                    details.value = api.getTvShowDetails(id, apiKey)
                    cast.value = api.getTvShowCredits(id, apiKey).cast
                    similar.value = api.getSimilarTvShows(id, apiKey).results
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}