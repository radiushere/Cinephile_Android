package com.example.cinephile.ui.screens.categories

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinephile.data.remote.TmdbApi
import com.example.cinephile.data.remote.dto.MovieDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val api: TmdbApi, private val apiKey: String) : ViewModel() {

    val movies = mutableStateOf<List<MovieDto>>(emptyList())
    val isLoading = mutableStateOf(false)

    val filtersVisible = mutableStateOf(false)
    val sortBy = mutableStateOf("popularity.desc")
    val selectedGenreId = mutableStateOf<String?>(null)
    val ratingGte = mutableStateOf(0f)
    val language = mutableStateOf("en")

    val genres = mapOf("Action" to "28","Adventure" to "12","Animation" to "16","Comedy" to "35","Crime" to "80","Documentary" to "99","Drama" to "18","Family" to "10751","Fantasy" to "14","History" to "36","Horror" to "27","Music" to "10402","Mystery" to "9648","Romance" to "10749","Science Fiction" to "878","TV Movie" to "10770","Thriller" to "53","War" to "10752","Western" to "37")
    val sortByOptions = mapOf("Popularity Descending" to "popularity.desc","Popularity Ascending" to "popularity.asc","Rating Descending" to "vote_average.desc","Rating Ascending" to "vote_average.asc","Release Date Descending" to "primary_release_date.desc","Release Date Ascending" to "primary_release_date.asc")

    init { discoverMovies() }

    fun onFiltersChanged() {
        discoverMovies()
    }

    private fun discoverMovies() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                movies.value = api.discoverMovies(
                    apiKey = apiKey,
                    sortBy = sortBy.value,
                    withGenres = selectedGenreId.value,
                    voteAverageGte = if (ratingGte.value > 0f) ratingGte.value else null,
                    withLanguage = language.value.ifBlank { null }
                ).results
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}