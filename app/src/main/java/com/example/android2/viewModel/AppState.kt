package com.example.android2.viewModel

import com.example.android2.model.Movie
import com.example.android2.model.MoviesList

sealed class AppState {
    data class Success(val movieData: List<Movie>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}