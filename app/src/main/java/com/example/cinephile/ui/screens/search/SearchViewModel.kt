package com.example.cinephile.ui.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinephile.data.remote.TmdbApi
import com.example.cinephile.data.remote.dto.MovieDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: TmdbApi,
    private val apiKey: String
) : ViewModel() {
    val query = mutableStateOf("")
    val results = mutableStateOf<List<MovieDto>>(emptyList())
    val isLoading = mutableStateOf(false)

    fun onQueryChange(newQuery: String) {
        query.value = newQuery
        if (newQuery.length > 2) {
            search()
        } else {
            results.value = emptyList()
        }
    }

    private fun search() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // --- THIS IS THE CORRECTED LINE ---
                results.value = api.searchMulti(apiKey, query.value).results
                    .filter { it.mediaType == "movie" || it.mediaType == "tv" }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}