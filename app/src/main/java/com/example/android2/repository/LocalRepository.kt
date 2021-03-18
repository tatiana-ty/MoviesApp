package com.example.android2.repository

import com.example.android2.model.Movie

interface LocalRepository {
    fun getAllHistory(): List<Movie>
    fun saveEntity(movie: Movie)
}