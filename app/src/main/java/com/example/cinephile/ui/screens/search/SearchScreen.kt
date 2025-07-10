package com.example.cinephile.ui.screens.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinephile.ui.components.MovieCard

// CORRECTED SIGNATURE
@Composable
fun SearchScreen(onItemClick: (mediaType: String, mediaId: Int) -> Unit) {
    val viewModel: SearchViewModel = hiltViewModel()
    val query by viewModel.query
    val results by viewModel.results
    val isLoading by viewModel.isLoading

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onQueryChange(it) },
            label = { Text("Search for movies or TV shows...") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 160.dp),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(results, key = { it.id }) { item ->
                    val mediaType = item.mediaType ?: "movie" // Default to movie if null
                    MovieCard(
                        posterPath = item.posterPath,
                        title = item.title ?: item.name ?: "Unknown",
                        onMovieClick = { onItemClick(mediaType, item.id) }
                    )
                }
            }
        }
    }
}