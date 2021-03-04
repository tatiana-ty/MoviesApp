package com.example.android2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val title: String = "Default Name",
    val rating: Double = 0.0,
    val year: String = "0000",
    val country: String = "Default",
    val genre: String = "Default",
    val description: String = "Some Description",
    val image: String = "Image"
) : Parcelable

var listOfMovies = getMovieList()
fun getMovieList() = listOf(
    Movie(
        "Film1",
        4.5,
        "2020",
        "US",
        "Comedy",
        "dfghjgf",
        "image"
    ),
    Movie(
        "Film2",
        4.0,
        "2010",
        "Russia",
        "Horror",
        "dfghjgf",
        "image"
    ),
    Movie(
        "Film3",
        4.8,
        "2015",
        "France",
        "Comedy",
        "dfghjgf",
        "image"
    ),
    Movie(
        "Film4",
        4.5,
        "2020",
        "US",
        "Comedy",
        "dfghjgf",
        "image"
    ),
    Movie(
        "Film5",
        4.0,
        "2010",
        "Russia",
        "Horror",
        "dfghjgf",
        "image"
    ),
    Movie(
        "Film6",
        4.8,
        "2015",
        "France",
        "Comedy",
        "dfghjgf",
        "image"
    )
)
