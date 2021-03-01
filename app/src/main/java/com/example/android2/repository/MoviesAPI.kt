package com.example.android2.repository

import com.example.android2.model.MovieDTO
import com.example.android2.model.MoviesList
import com.example.android2.model.MoviesListDTO
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MoviesAPI {
    @GET("3/movie/{movieId}")
    fun getMovie(
        @Path("movieId") movieId: String,
        @Query("api_key") token: String
    ): Call<MovieDTO>

    @GET("3/movie/{type}")
    fun getListOfMovies(
        @Path("type") type: String,
        @Query("api_key") token: String
    ): Call<MoviesListDTO>

}