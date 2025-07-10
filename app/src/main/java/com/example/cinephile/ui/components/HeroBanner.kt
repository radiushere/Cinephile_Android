package com.example.cinephile.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinephile.data.remote.dto.MovieDto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeroBanner(movies: List<MovieDto>, onItemClick: (mediaType: String, mediaId: Int) -> Unit) {
    val pagerState = rememberPagerState(pageCount = { movies.size })

    Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
        HorizontalPager(state = pagerState) { page ->
            val movie = movies[page]
            Box(modifier = Modifier.fillMaxSize().clickable { onItemClick("movie", movie.id) }) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w1280${movie.backdropPath}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Brush.verticalGradient(
                            colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                            startY = 600f
                        ))
                )
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Text(
                        text = movie.title ?: movie.name ?: "Unknown",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Row(
            Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                Box(modifier = Modifier.padding(2.dp).clip(CircleShape).background(color).size(8.dp))
            }
        }
    }
}