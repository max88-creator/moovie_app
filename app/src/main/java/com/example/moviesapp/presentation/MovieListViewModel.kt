package com.example.moviesapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.movieList.domain.repository.MovieListRepository
import com.example.moviesapp.movieList.util.Category
import com.example.moviesapp.movieList.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieListRepository
) : ViewModel() {
    private val _movieState = MutableStateFlow(MovieListState())
    val movieState = _movieState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            is MovieListUiEvent.Paginate -> {
                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }
            }

            is MovieListUiEvent.Navigate -> {
                _movieState.update { state ->
                    state.copy(
                        isCurrentPopularScreen = !movieState.value.isCurrentPopularScreen
                    )
                }
            }
        }
    }

    fun getPopularMovieList(
        forceFetchFromRemote: Boolean
    ) {
        viewModelScope.launch {
            _movieState.update { state ->
                state.copy(
                    isLoading = true
                )
            }
            repository.getMovieList(
                forceFetchFromRemote = forceFetchFromRemote,
                category = Category.POPULAR,
                page = movieState.value.popularMovieListPage
            )
                .collectLatest { resources ->
                    when (resources) {
                        is Resources.Success<*> -> {
                            resources.data?.let { popularList ->
                                _movieState.update { state ->
                                    state.copy(
                                        popularMovieList = movieState.value.popularMovieList + popularList.shuffled(),
                                        popularMovieListPage = movieState.value.popularMovieListPage + 1
                                    )
                                }
                            }
                        }

                        is Resources.Loading<*> -> {
                            _movieState.update { state ->
                                state.copy(
                                    isLoading = resources.loading
                                )
                            }
                        }

                        is Resources.Error<*> -> {
                            _movieState.update { state ->
                                state.copy(
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
        }
    }

    fun getUpcomingMovieList(
        forceFetchFromRemote: Boolean
    ) {
        viewModelScope.launch {
            _movieState.update { state ->
                state.copy(
                    isLoading = true
                )
            }
            repository.getMovieList(
                forceFetchFromRemote = forceFetchFromRemote,
                category = Category.UPCOMING,
                page = movieState.value.upcomingMovieListPage
            )
                .collectLatest { resources ->
                    when (resources) {
                        is Resources.Success<*> -> {
                            resources.data?.let { upcomingList ->
                                _movieState.update { state ->
                                    state.copy(
                                        upcomingMovieList = movieState.value.upcomingMovieList + upcomingList.shuffled(),
                                        upcomingMovieListPage = movieState.value.upcomingMovieListPage + 1
                                    )
                                }
                            }
                        }

                        is Resources.Loading<*> -> {
                            _movieState.update { state ->
                                state.copy(
                                    isLoading = resources.loading
                                )
                            }
                        }

                        is Resources.Error<*> -> {
                            _movieState.update { state ->
                                state.copy(
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
        }
    }
}