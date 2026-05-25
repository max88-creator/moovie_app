package com.example.moviesapp.movieList.data.remote
import com.example.moviesapp.BuildConfig
import com.example.moviesapp.movieList.data.remote.respond.MovieDto
import com.example.moviesapp.movieList.data.remote.respond.MovieListDto
import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMovieList(
    @Path("category") category: String,
    @Query("page") page: Int,
    @Query("api_key") apiKey: String = API_KEY): MovieListDto

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"
        const val API_KEY = BuildConfig.MOVIE_API_KEY
    }
}


