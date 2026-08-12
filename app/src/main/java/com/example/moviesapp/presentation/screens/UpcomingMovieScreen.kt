package com.example.moviesapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviesapp.movieList.domain.model.Movie
import com.example.moviesapp.movieList.util.Category
import com.example.moviesapp.presentation.MovieListState
import com.example.moviesapp.presentation.MovieListUiEvent
import com.example.moviesapp.presentation.component.MovieItem

@Composable
fun UpcomingMovieScreen(
    state: MovieListState,
    onEvent: (MovieListUiEvent) -> Unit,
    navController: NavHostController
) {
    if (state.upcomingMovieList.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                horizontal = 4.dp,
                vertical = 8.dp
            )
        ) {
            items(state.upcomingMovieList.size) { index ->
                MovieItem(
                    state.upcomingMovieList[index],
                    navController
                )
                Spacer(modifier = Modifier.height(16.dp))
                if (index >= state.upcomingMovieList.size - 1 && !state.isLoading) {
                    onEvent(MovieListUiEvent.Paginate(Category.UPCOMING))
                }
            }
        }
    }
}