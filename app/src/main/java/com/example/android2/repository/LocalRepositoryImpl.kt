package com.example.android2.repository

import com.example.android2.model.Movie
import com.example.android2.model.room.HistoryDao
import com.example.android2.model.room.HistoryEntity

class LocalRepositoryImpl(private val localDataSource: HistoryDao) : LocalRepository {

    override fun getAllHistory(): List<Movie> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(movie: Movie) {
        localDataSource.insert(convertWeatherToEntity(movie))
    }

    fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Movie> {
        return entityList.map {
            Movie(it.title, it.rating, it.country, it.genre, it.description, it.image, it.note)
        }
    }

    fun convertWeatherToEntity(movie: Movie): HistoryEntity {
        return HistoryEntity(0, movie.title, movie.rating, movie.country, movie.genre,
        movie.description, movie.image, movie.note)
    }
}