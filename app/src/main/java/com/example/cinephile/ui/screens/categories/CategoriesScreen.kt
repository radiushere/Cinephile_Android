package com.example.cinephile.ui.screens.categories

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinephile.ui.components.MovieCard

// CORRECTED SIGNATURE
@Composable
fun CategoriesScreen(onMovieClick: (Int) -> Unit) {
    val viewModel: CategoriesViewModel = hiltViewModel()
    val movies by viewModel.movies
    val isLoading by viewModel.isLoading
    val filtersVisible by viewModel.filtersVisible

    Column(Modifier.fillMaxSize()) {
        Button(
            onClick = { viewModel.filtersVisible.value = !filtersVisible },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(if (filtersVisible) "Hide Filters" else "Show All Filters")
        }
        AnimatedVisibility(visible = filtersVisible, enter = fadeIn() + expandVertically(), exit = fadeOut() + shrinkVertically()) {
            FilterPanel(viewModel)
        }
        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(160.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies, key = { it.id }) { movie ->
                    MovieCard(
                        posterPath = movie.posterPath,
                        title = movie.title ?: movie.name ?: "Unknown",
                        onMovieClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterPanel(viewModel: CategoriesViewModel) {
    Column(Modifier
        .padding(horizontal = 16.dp)
        .padding(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Sort By
        var sortByExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = sortByExpanded, onExpandedChange = { sortByExpanded = !sortByExpanded }) {
            OutlinedTextField(value = viewModel.sortByOptions.entries.find { it.value == viewModel.sortBy.value }?.key ?: "", onValueChange = {}, readOnly = true, label = { Text("Sort By") }, modifier = Modifier.menuAnchor().fillMaxWidth(), trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = sortByExpanded) })
            ExposedDropdownMenu(expanded = sortByExpanded, onDismissRequest = { sortByExpanded = false }) {
                viewModel.sortByOptions.forEach { (name, value) ->
                    DropdownMenuItem(text = { Text(name) }, onClick = { viewModel.sortBy.value = value; sortByExpanded = false })
                }
            }
        }
        // Genre
        var genreExpanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(expanded = genreExpanded, onExpandedChange = { genreExpanded = !genreExpanded }) {
            OutlinedTextField(value = viewModel.genres.entries.find { it.value == viewModel.selectedGenreId.value }?.key ?: "Any Genre", onValueChange = {}, readOnly = true, label = { Text("Genre") }, modifier = Modifier.menuAnchor().fillMaxWidth(), trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = genreExpanded) })
            ExposedDropdownMenu(expanded = genreExpanded, onDismissRequest = { genreExpanded = false }) {
                DropdownMenuItem(text = { Text("Any Genre") }, onClick = { viewModel.selectedGenreId.value = null; genreExpanded = false })
                viewModel.genres.forEach { (name, id) ->
                    DropdownMenuItem(text = { Text(name) }, onClick = { viewModel.selectedGenreId.value = id; genreExpanded = false })
                }
            }
        }
        // Language
        OutlinedTextField(value = viewModel.language.value, onValueChange = { viewModel.language.value = it }, label = { Text("Language (e.g., en, es, fr)") }, modifier = Modifier.fillMaxWidth())
        // Rating
        Text("Minimum Rating: ${String.format("%.1f", viewModel.ratingGte.value)}", style = MaterialTheme.typography.labelMedium)
        Slider(value = viewModel.ratingGte.value, onValueChange = { viewModel.ratingGte.value = it }, valueRange = 0f..10f, steps = 19)
        // Apply Button
        Button(onClick = { viewModel.onFiltersChanged() }, modifier = Modifier.fillMaxWidth()) {
            Text("Apply Filters & Search")
        }
    }
}