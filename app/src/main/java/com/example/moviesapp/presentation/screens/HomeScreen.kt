package com.example.moviesapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moviesapp.movieList.util.Screens
import com.example.moviesapp.presentation.MovieListUiEvent
import com.example.moviesapp.presentation.MovieListViewModel
import com.example.moviesapp.presentation.component.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MovieListViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val state = viewModel.movieState.collectAsState().value
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavBar(
                onEvent =
                    viewModel::onEvent,
                navHostController = bottomNavController,
            )
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                ),
                title = {
                    Text(
                        text = if (state.isCurrentPopularScreen) {
                            "Popular Movies"
                        } else {
                            "Upcoming Movies"
                        },
                        fontSize = 20.sp,
                        modifier = Modifier
                            .shadow(2.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                startDestination = Screens.PopularMovieList.route,
                navController = bottomNavController
            ) {
                composable(Screens.PopularMovieList.route) {
                    PopularMovieScreen(
                        state = state,
                        onEvent = viewModel::onEvent,
                        navHostController = navHostController
                    )
                }
                composable(Screens.Upcoming.route) {
                    UpcomingMovieScreen(
                        state = state,
                        onEvent = { event ->
                            viewModel.onEvent(event)
                        },
                        navController = navHostController
                    )
                }
            }
        }
    }
}
