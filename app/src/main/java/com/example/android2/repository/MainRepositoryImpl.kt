package com.example.android2.repository

import com.example.android2.model.*

class MainRepositoryImpl: MainRepository {
    override fun getMovieFromServer() =  Movie()

    override fun getMovieInfo() = listOfMovies
}
