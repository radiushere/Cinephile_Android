package com.example.cinephile.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Categories : Screen("categories")
    object Search : Screen("search")
    object Settings : Screen("settings")
    object About : Screen("about")
    object MovieDetail : Screen("mediaDetail/{mediaType}/{mediaId}") {
        fun createRoute(mediaType: String, mediaId: Int) = "mediaDetail/$mediaType/$mediaId"
    }
}