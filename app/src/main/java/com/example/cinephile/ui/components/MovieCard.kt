package com.example.cinephile.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cinephile.R // It's okay to keep this import

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    posterPath: String?,
    title: String,
    onMovieClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(160.dp)
            .padding(8.dp)
            .clickable(onClick = onMovieClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500$posterPath",
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                // --- THIS IS THE IMMEDIATE FIX ---
                // We use a simple grey color as the placeholder and error image.
                placeholder = ColorPainter(Color.Gray.copy(alpha = 0.3f)),
                error = ColorPainter(Color.Gray.copy(alpha = 0.3f))
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}