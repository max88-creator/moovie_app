package com.example.moviesapp.movieList.util

import android.os.Message

sealed class Resources<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?): Resources<T>(data)
    class Error<T>(message: String, data: T? = null): Resources<T>(data, message)
    class Loading<T>(val loading: Boolean = true): Resources<T>(null)
}