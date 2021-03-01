package com.example.android2.utils

import com.example.android2.model.*

fun convertDtoToModel(movieDTO: MovieDTO): List<Movie> {
    val genresList: List<GenreDTO> = movieDTO.genres!!
    var genres = ""
    for (item in genresList) {
        genres += item.name + " "
        if(genresList.indexOf(item) < genresList.size) genres += ", "
    }
    val countriesList: List<CountryDTO> = movieDTO.countries!!
    var countries = ""
    for (item in countriesList) {
        countries += item.name
        if(countriesList.indexOf(item) < countriesList.size) countries += ", "
    }
    return listOf(Movie(
        movieDTO.title!!,
        movieDTO.rating!!,
        movieDTO.year!!,
        countries,
        genres,
        movieDTO.overview!!,
        movieDTO.image!!
        ))
}