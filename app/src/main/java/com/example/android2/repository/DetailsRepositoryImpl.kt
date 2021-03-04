package com.example.android2.repository

import com.example.android2.model.MovieDTO
import retrofit2.Callback

class DetailsRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DetailsRepository {

    override fun getMovieDetailsFromServer(
        movieId: String,
        callback: Callback<MovieDTO>
    ) {
        remoteDataSource.getMovieDetails(movieId, callback)
    }
}