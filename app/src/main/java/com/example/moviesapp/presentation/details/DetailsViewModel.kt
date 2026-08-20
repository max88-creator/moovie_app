package com.example.moviesapp.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.movieList.domain.repository.MovieListRepository
import com.example.moviesapp.movieList.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val repository: MovieListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(value = DetailsState())
    val state = _state.asStateFlow()
    private val movieId = savedStateHandle.get(
        key = "movieId"
    ) ?: -1

    init {
        getMovie(
            id = movieId
        )
    }

    private fun getMovie(
        id: Int
    ) {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true
                )
            }
            repository.getMovie(id).collectLatest { result ->
                when (result) {
                    is Resources.Error<*> -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                movie = null
                            )
                        }
                    }
                    is Resources.Loading<*> -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = result.loading
                            )
                        }
                    }
                    is Resources.Success<*> -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                movie = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}