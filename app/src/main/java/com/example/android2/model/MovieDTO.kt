package com.example.android2.model

import com.google.gson.annotations.SerializedName

data class MovieDTO (
        val title: String?,

        @SerializedName("vote_average")
        val rating: Double?,
        @SerializedName("release_date")
        val year: String?,
        @SerializedName("production_countries")
        val countries: List<CountryDTO>?,

        val genres: List<GenreDTO>?,
        val overview: String?,
        @SerializedName("poster_path")
        val image: String?
)

data class CountryDTO (
        val name: String?
)

data class GenreDTO (
        val name: String?
)