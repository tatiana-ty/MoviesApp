package com.example.android2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val name: String,
    val rating: String,
    val year: String,
    val country: String,
    val genre: String
) : Parcelable

fun getWorldCities(): List<Film> {
    return listOf(
        Film("Film1", "4.5", "2020", "US", "Comedy"),
        Film("Film2", "4.0", "2010", "Russia", "Horror"),
        Film("Film3", "4.8", "2015", "France", "Comedy"),
        Film("Film4", "3.8", "2018", "US", "Drama"),
        Film("Film5", "3.6", "2012", "GB", "Action"),
        Film("Film6", "4.4", "2021", "Germany", "Romance")
    )
}