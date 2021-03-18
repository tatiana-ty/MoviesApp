package com.example.android2.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val rating: Double,
    val country: String,
    val genre: String,
    val description: String,
    val image: String,
    val note: String
)
