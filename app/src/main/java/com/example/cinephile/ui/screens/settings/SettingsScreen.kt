package com.example.cinephile.ui.screens.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cinephile.ui.theme.AppTheme

@Composable
fun SettingsScreen(
    currentTheme: AppTheme, // ADDED: To know which theme is active
    onThemeSelected: (AppTheme) -> Unit,
    onDarkModeToggle: () -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background.red < 0.5f // Simple check for dark mode
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Theme Options", style = MaterialTheme.typography.headlineSmall)
        Row(
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onDarkModeToggle),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Dark Mode", style = MaterialTheme.typography.titleMedium)
            Switch(checked = isDark, onCheckedChange = { onDarkModeToggle() })
        }
        Divider()
        AppTheme.values().forEach { theme ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { onThemeSelected(theme) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                // CORRECTED: Compare the enum directly, not a color to a string
                RadioButton(selected = currentTheme == theme, onClick = { onThemeSelected(theme) })
                Spacer(Modifier.width(8.dp))
                Text(theme.name)
            }
        }
    }
}