package com.example.cinephile.ui.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.cinephile.data.remote.dto.CastMemberDto
import com.example.cinephile.ui.components.MovieCard

@Composable
fun MovieDetailScreen(onSimilarMovieClick: (mediaType: String, mediaId: Int) -> Unit) {
    val viewModel: MovieDetailViewModel = hiltViewModel()
    val details by viewModel.details
    val cast by viewModel.cast
    val similar by viewModel.similar
    val isLoading by viewModel.isLoading
    val mediaType by viewModel.mediaType

    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        details?.let { movie ->
            Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                AsyncImage(model = "https://image.tmdb.org/t/p/w1280${movie.backdropPath}", contentDescription = null, modifier = Modifier.fillMaxWidth().height(250.dp), contentScale = ContentScale.Crop)
                Column(Modifier.padding(16.dp)) {
                    Text(text = movie.title ?: movie.name ?: "Unknown Title", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(movie.genres?.joinToString { it.name } ?: "", style = MaterialTheme.typography.bodyMedium)
                        Text("•")
                        Text((movie.releaseDate ?: movie.firstAirDate ?: "").take(4), style = MaterialTheme.typography.bodyMedium)
                        Text("•")
                        Text("⭐ ${String.format("%.1f", movie.voteAverage)}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = Color(0xFFFFC107))
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Overview", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text(movie.overview ?: "No overview available.", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(24.dp))
                    Text("Cast", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(cast) { castMember -> CastMember(castMember) }
                    }
                    Spacer(Modifier.height(24.dp))
                    Text("Similar", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                        items(similar, key = { it.id }) { similarItem ->
                            MovieCard(
                                posterPath = similarItem.posterPath,
                                title = similarItem.title ?: similarItem.name ?: "Unknown",
                                onMovieClick = { onSimilarMovieClick(mediaType, similarItem.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CastMember(member: CastMemberDto) {
    Column(Modifier.width(90.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(model = "https://image.tmdb.org/t/p/w185${member.profilePath}", contentDescription = member.name, modifier = Modifier.size(80.dp).clip(CircleShape), contentScale = ContentScale.Crop)
        Spacer(Modifier.height(4.dp))
        Text(member.name, fontSize = 12.sp, textAlign = TextAlign.Center, maxLines = 2)
    }
}