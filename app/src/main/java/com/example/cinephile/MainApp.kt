package com.example.cinephile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.cinephile.ui.navigation.Screen
import com.example.cinephile.ui.screens.about.AboutScreen
import com.example.cinephile.ui.screens.categories.CategoriesScreen
import com.example.cinephile.ui.screens.detail.MovieDetailScreen
import com.example.cinephile.ui.screens.home.HomeScreen
import com.example.cinephile.ui.screens.search.SearchScreen
import com.example.cinephile.ui.screens.settings.SettingsScreen
import com.example.cinephile.ui.theme.AppTheme
import com.example.cinephile.ui.theme.CinePhileTheme
import kotlinx.coroutines.launch

@Composable
fun MainApp() {
    var currentTheme by remember { mutableStateOf(AppTheme.Default) }
    var isDarkMode by remember { mutableStateOf(true) }

    CinePhileTheme(theme = currentTheme, darkTheme = isDarkMode) {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route ?: Screen.Home.route

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                AppDrawer(currentRoute = currentRoute, onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                    scope.launch { drawerState.close() }
                })
            }
        ) {
            Scaffold(topBar = {
                AppTopBar(currentRoute = currentRoute,
                    onNavigationIconClick = { scope.launch { drawerState.open() } },
                    onSearchClick = { navController.navigate(Screen.Search.route) },
                    onSettingsClick = { navController.navigate(Screen.Settings.route) },
                    onBackClick = { navController.popBackStack() }
                )
            }) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    AppNavigation(
                        navController = navController,
                        // Pass the current theme state down to the navigator
                        currentTheme = currentTheme,
                        onThemeSelected = { theme -> currentTheme = theme },
                        onDarkModeToggle = { isDarkMode = !isDarkMode }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(currentRoute: String, onNavigationIconClick: () -> Unit, onSearchClick: () -> Unit, onSettingsClick: () -> Unit, onBackClick: () -> Unit) {
    val showBackButton = currentRoute.startsWith("mediaDetail")
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
            } else {
                IconButton(onClick = onNavigationIconClick) { Icon(Icons.Default.Menu, "Menu") }
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) { Icon(Icons.Default.Search, "Search") }
            IconButton(onClick = onSettingsClick) { Icon(Icons.Default.Settings, "Settings") }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.8f))
    )
}

@Composable
fun AppDrawer(currentRoute: String, onNavigate: (String) -> Unit) {
    ModalDrawerSheet(drawerContainerColor = MaterialTheme.colorScheme.background) {
        Column {
            // This Box now holds your logo instead of the colored background and text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)), // A subtle background
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cinephile_black), // <-- USE YOUR LOGO'S FILENAME HERE
                    contentDescription = "CinePhile Logo",
                    modifier = Modifier.padding(16.dp) // Add some padding around the logo
                )
            }
            Spacer(Modifier.height(12.dp))
            // The DrawerItems below remain the same
            DrawerItem(icon = Icons.Default.Home, label = "Home", isSelected = currentRoute == Screen.Home.route, action = { onNavigate(Screen.Home.route) })
            DrawerItem(icon = Icons.Default.Category, label = "Categories", isSelected = currentRoute == Screen.Categories.route, action = { onNavigate(Screen.Categories.route) })
            DrawerItem(icon = Icons.Default.Info, label = "About", isSelected = currentRoute == Screen.About.route, action = { onNavigate(Screen.About.route) })
        }
    }
}

@Composable
fun DrawerItem(icon: ImageVector, label: String, isSelected: Boolean, action: () -> Unit) {
    NavigationDrawerItem(
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        selected = isSelected,
        onClick = action,
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    currentTheme: AppTheme, // ADDED
    onThemeSelected: (AppTheme) -> Unit,
    onDarkModeToggle: () -> Unit
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(onItemClick = { mediaType, mediaId ->
                navController.navigate(Screen.MovieDetail.createRoute(mediaType, mediaId))
            })
        }
        composable(Screen.Categories.route) {
            // This now correctly calls onMovieClick
            CategoriesScreen(onMovieClick = { mediaId ->
                navController.navigate(Screen.MovieDetail.createRoute("movie", mediaId))
            })
        }
        composable(Screen.Search.route) {
            // This now correctly calls onItemClick
            SearchScreen(onItemClick = { mediaType, mediaId ->
                navController.navigate(Screen.MovieDetail.createRoute(mediaType, mediaId))
            })
        }
        composable(Screen.Settings.route) {
            // This now passes the currentTheme correctly
            SettingsScreen(
                currentTheme = currentTheme,
                onThemeSelected = onThemeSelected,
                onDarkModeToggle = onDarkModeToggle
            )
        }
        composable(Screen.About.route) { AboutScreen() }
        composable(
            route = Screen.MovieDetail.route,
            arguments = listOf(
                navArgument("mediaType") { type = NavType.StringType },
                navArgument("mediaId") { type = NavType.IntType }
            )
        ) {
            MovieDetailScreen(onSimilarMovieClick = { mediaType, mediaId ->
                navController.navigate(Screen.MovieDetail.createRoute(mediaType, mediaId)) { launchSingleTop = true }
            })
        }
    }
}