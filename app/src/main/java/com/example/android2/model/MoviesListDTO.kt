package com.example.android2.model

import com.google.gson.annotations.SerializedName

data class MoviesListDTO (
    val results: List<ResultsDTO>?
)

data class ResultsDTO (
    val adult: Boolean?,
    val id: Int?,
    val title: String?,
    @SerializedName("poster_path")
    val image: String?
)