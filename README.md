# 🎬 CinePhile

![CinePhile Banner](https://raw.githubusercontent.com/radiushere/Cinephile_Android/main/screenshots/banner.png)

**Your personal guide to the world of cinema. Built with 100% Jetpack Compose and modern Android development practices.**

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/radiushere/Cinephile_Android/blob/main/LICENSE)
[![Latest Release](https://img.shields.io/github/v/release/radiushere/Cinephile_Android)](https://github.com/radiushere/Cinephile_Android/releases/latest)
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.0-blue.svg?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)

---

CinePhile is a sleek, modern Android application designed for movie and TV show enthusiasts. It provides a rich, immersive experience for discovering new and trending media, exploring detailed information, and personalizing your viewing journey. Built entirely with Jetpack Compose and powered by The Movie Database (TMDB) API, this project serves as a showcase of modern Android development techniques.

## ✨ Features

*   **Dynamic Home Screen**: A beautifully designed dashboard featuring a swipeable **Hero Banner** for currently playing movies, followed by curated sections like "Trending This Week," "Top Rated Movies," and "Popular TV Shows."
*   **Advanced Discovery Engine**: A powerful "Categories" page with a collapsible filter panel. Find the perfect movie by filtering and sorting by:
    *   **Sort Order**: Popularity, Rating, or Release Date.
    *   **Genre**: Action, Comedy, Drama, and more.
    *   **Minimum User Rating**: Use a slider to find critically acclaimed films.
    *   **Original Language**: Discover films from around the world.
*   **Multi-Theming Engine**: Personalize your experience! Choose from multiple app themes in the Settings menu, including a default Red & Black, "Ocean" (Deep Blue & Gold), and "Forest" (Jungle Green & Orange), each with full dark mode support.
*   **Seamless Movie & TV Differentiation**: The app intelligently distinguishes between movies and TV shows, fetching the correct details, cast, and similar titles for each.
*   **Detailed Insights**: Dive deep into any movie or TV show. The detail screen showcases a high-resolution backdrop, summary, genres, ratings, and horizontally scrolling lists for cast members and similar titles.
*   **Universal Search**: Quickly find any movie or TV show with the powerful multi-search functionality.
*   **Polished & Optimized UI**: From a customized navigation drawer with your logo to smooth animations and optimized lists, the UI is designed to be both beautiful and performant.

## 📸 Showcase

| Home Screen                                                                        | Categories (Filters Open)                                                                       | Detail Screen                                                                     |
| ---------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- |
| ![Home Screen](https://raw.githubusercontent.com/radiushere/Cinephile_Android/main/screenshots/screenshot_home.png) | ![Categories](https://raw.githubusercontent.com/radiushere/Cinephile_Android/main/screenshots/screenshot_categories.png) | ![Detail](https://raw.githubusercontent.com/radiushere/Cinephile_Android/main/screenshots/screenshot_detail.png) |

## 🚀 Tech Stack & Architecture

This project is built upon a solid **MVVM (Model-View-ViewModel)** architecture and showcases a wide range of modern Android technologies.

*   **Language**: **[Kotlin](https://kotlinlang.org/)** (100%)
*   **UI Toolkit**: **[Jetpack Compose](https://developer.android.com/jetpack/compose)**
*   **Asynchronous**: **[Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** for managing background threads.
*   **Dependency Injection**: **[Hilt](https://dagger.dev/hilt/)** for managing dependencies and object lifecycles.
*   **Networking**: **[Retrofit](https://square.github.io/retrofit/)** for type-safe REST API calls.
*   **Image Loading**: **[Coil](https://coil-kt.github.io/coil/)** for efficient image loading with Compose support.
*   **Navigation**: **[Jetpack Navigation for Compose](https://developer.android.com/jetpack/compose/navigation)** for navigating between screens.
*   **Modern Tooling**: Version Catalog (`libs.versions.toml`) for clean dependency management.

## 🛠️ Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

*   Android Studio Iguana | 2023.2.1 or newer
*   JDK 17 or higher

### Setup

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/radiushere/Cinephile_Android.git
    ```
2.  **Open the project** in Android Studio.
3.  **Get a TMDB API Key:**
    *   This project requires an API key from The Movie Database. You can get one for free by creating an account at [themoviedb.org](https://www.themoviedb.org/).
    *   Go to `Settings -> API` to find your key.
4.  **Create your `local.properties` file:**
    *   In the root directory of the project, create a new file named `local.properties`.
    *   Add your TMDB API key to this file like so (without any quotes):
      ```properties
      TMDB_API_KEY=YOUR_API_KEY_HERE
      ```
5.  **Sync Gradle and run the app!**

## 📲 Installation

You can install the latest version of the app directly on your Android device.

1.  Go to the **[Releases](https://github.com/radiushere/Cinephile_Android/releases)** page.
2.  Download the `app-release.apk` file from the latest release assets.
3.  Open the file on your device and follow the installation instructions. You may need to enable **"Install from unknown sources"** in your phone's settings.

## 🙏 Acknowledgments

*   This application uses the [TMDb API](https://www.themoviedb.org/documentation/api) but is not endorsed or certified by TMDb.
*   Huge thanks to all the developers behind the open-source libraries that made this project possible.
