package com.example.cinephile.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinephile.data.remote.dto.MovieDto
import com.example.cinephile.ui.components.HeroBanner
import com.example.cinephile.ui.components.MovieCard

// CORRECTED SIGNATURE
@Composable
fun HomeScreen(onItemClick: (mediaType: String, mediaId: Int) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val heroBannerItems by viewModel.heroBannerItems
    val trendingItems by viewModel.trendingItems
    val topRatedMovies by viewModel.topRatedMovies
    val popularTvShows by viewModel.popularTvShows
    val isLoading by viewModel.isLoading

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        LazyColumn(Modifier.fillMaxSize()) {
            if (heroBannerItems.isNotEmpty()) {
                // Pass onItemClick to the HeroBanner
                item { HeroBanner(movies = heroBannerItems.take(5), onItemClick = onItemClick) }
            }
            item { HomeSection("Trending This Week", trendingItems, onItemClick) }
            item { HomeSection("Top Rated Movies", topRatedMovies, onItemClick, "movie") }
            item { HomeSection("Popular TV Shows", popularTvShows, onItemClick, "tv") }
        }
    }
}

@Composable
private fun HomeSection(title: String, items: List<MovieDto>, onItemClick: (mediaType: String, mediaId: Int) -> Unit, defaultMediaType: String? = null) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(8.dp))
        LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(items, key = { it.id }) { item ->
                val mediaType = item.mediaType ?: defaultMediaType ?: "movie"
                MovieCard(
                    posterPath = item.posterPath,
                    title = item.title ?: item.name ?: "Unknown",
                    onMovieClick = { onItemClick(mediaType, item.id) }
                )
            }
        }
    }
}