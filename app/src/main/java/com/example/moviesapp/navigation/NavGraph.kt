package com.example.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviesapp.movieList.util.Screens
import com.example.moviesapp.presentation.details.DetailsScreen
import com.example.moviesapp.presentation.screens.HomeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen(
                navHostController = navController
            )
        }
        composable(
          route = Screens.Details.route + "/{movieId}" ,
            arguments = listOf(navArgument(name = "movieId") {
                type = NavType.IntType
            })
        ) {
            DetailsScreen()
        }
    }
}