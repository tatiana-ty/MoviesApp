package com.example.android2.repository

import com.example.android2.model.Movie

interface MainRepository {
    fun getMovieFromServer(): Movie
    fun getMovieInfo(): List<Movie>
}
