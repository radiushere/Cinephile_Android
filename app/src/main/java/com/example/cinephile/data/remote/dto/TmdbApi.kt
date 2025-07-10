package com.example.cinephile.data.remote

import com.example.cinephile.data.remote.dto.CreditsDto
import com.example.cinephile.data.remote.dto.MovieDetailDto
import com.example.cinephile.data.remote.dto.TmdbResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    // --- Home Screen ---
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String): TmdbResponse

    @GET("trending/all/week")
    suspend fun getTrendingAll(@Query("api_key") apiKey: String): TmdbResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String): TmdbResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(@Query("api_key") apiKey: String): TmdbResponse

    // --- Categories Screen ---
    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("with_genres") withGenres: String?,
        @Query("vote_average.gte") voteAverageGte: Float?,
        @Query("with_original_language") withLanguage: String?
    ): TmdbResponse

    // --- Search Screen ---
    @GET("search/multi")
    suspend fun searchMulti(@Query("api_key") apiKey: String, @Query("query") query: String): TmdbResponse

    // --- Detail Screen (Movies) ---
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): MovieDetailDto

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): CreditsDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): TmdbResponse

    // --- Detail Screen (TV) ---
    @GET("tv/{tv_id}")
    suspend fun getTvShowDetails(@Path("tv_id") tvId: Int, @Query("api_key") apiKey: String): MovieDetailDto

    @GET("tv/{tv_id}/credits")
    suspend fun getTvShowCredits(@Path("tv_id") tvId: Int, @Query("api_key") apiKey: String): CreditsDto

    @GET("tv/{tv_id}/similar")
    suspend fun getSimilarTvShows(@Path("tv_id") tvId: Int, @Query("api_key") apiKey: String): TmdbResponse
}