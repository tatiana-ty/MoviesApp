package com.example.android2.repository

import com.example.android2.model.MovieDTO

interface DetailsRepository {
    fun getMovieDetailsFromServer(
        movieId: String,
        callback: retrofit2.Callback<MovieDTO>
    )
}