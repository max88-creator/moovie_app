package com.example.moviesapp.movieList.util

sealed class Screens(val route: String) {
  object Home: Screens("home")
  object PopularMovieList: Screens("popular")
  object Upcoming: Screens("upcoming")
  object UpcomingMovieList: Screens("upcomingMovieList")
  object Details: Screens("details")
}