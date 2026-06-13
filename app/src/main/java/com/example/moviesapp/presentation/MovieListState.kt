package com.example.moviesapp.presentation

import com.example.moviesapp.movieList.domain.model.Movie

data class MovieListState(
    val isCurrentPopularScreen: Boolean = true,
    val isLoading: Boolean = false,
    val popularMovieListPage: Int = 1,
    val upcomingMovieListPage: Int = 1,
    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList()
)