package com.example.moviesapp.movieList.domain.repository

import com.example.moviesapp.movieList.domain.model.Movie
import com.example.moviesapp.movieList.util.Resources
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resources<List<Movie>>>

    suspend fun getMovie(
        id: Int
    ): Flow<Resources<Movie>>
}

