package com.example.moviesapp.presentation.component

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.example.moviesapp.movieList.util.Screens
import com.example.moviesapp.presentation.MovieListUiEvent
import kotlinx.coroutines.selects.select

@Composable
fun BottomNavBar(
    onEvent: (MovieListUiEvent) -> Unit,
    navHostController: NavHostController
) {
    val items = listOf(
        BottomItem(
            "Popular",
            Icons.Rounded.Movie
        ),
        BottomItem(
            title = "Upcoming",
            Icons.Rounded.Upcoming
        )
    )

    val isSelected = rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        modifier = Modifier
            .background(
                MaterialTheme
                    .colorScheme
                    .inverseOnSurface
            )
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = isSelected.intValue == index,
                onClick = {
                    isSelected.intValue = index
                    when (isSelected.intValue) {
                        0 -> {
                            onEvent(MovieListUiEvent.Navigate)
                            navHostController.popBackStack()
                            navHostController.navigate(Screens.PopularMovieList.route)
                        }

                        1 -> {
                            onEvent(MovieListUiEvent.Navigate)
                            navHostController.popBackStack()
                            navHostController.navigate(Screens.Upcoming.route)
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
        }
    }
}


data class BottomItem(
    val title: String,
    val icon: ImageVector
)