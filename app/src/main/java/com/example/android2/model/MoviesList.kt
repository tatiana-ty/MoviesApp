package com.example.android2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesList(
    val adult: Boolean = false,
    val title: String = "Film Name",
    val image: String = "Some Image",
    val id: Int = 0
) : Parcelable

