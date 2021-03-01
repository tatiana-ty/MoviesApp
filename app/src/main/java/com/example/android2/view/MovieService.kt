package com.example.android2.view

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.android2.model.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val MOVIE_ID_EXTRA = "Movie ID"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

class MovieService(name: String = "MovieService") : IntentService(name) {

    private val broadcastIntent = Intent(MOVIE_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val movieId = intent.getStringExtra(MOVIE_ID_EXTRA)
            if (movieId == null) {
                onEmptyData()
            } else {
                loadMovie(movieId)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadMovie(movieId: String) {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/${movieId}")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                    addRequestProperty("api_key", MOVIES_API_KEY)
                }

                val movieDTO: MovieDTO =
                    Gson().fromJson(
                        getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))),
                        MovieDTO::class.java
                    )
                onResponse(movieDTO)
            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            onMalformedURL()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun onResponse(movieDTO: MovieDTO) {
        movieDTO?.let {
            onSuccessResponse(
                it.title,
                it.rating,
                it.year,
                it.countries,
                it.genres,
                it.overview,
                it.image
            )
        } ?: run { onEmptyResponse() }
    }

    private fun onSuccessResponse(title: String?, rating: Double?, year: String?,
    countriesList: List<CountryDTO>?, genresList: List<GenreDTO>?, overview: String?, image: String?) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TITLE_EXTRA, title)
        broadcastIntent.putExtra(DETAILS_RATING_EXTRA, rating)
        broadcastIntent.putExtra(DETAILS_YEAR_EXTRA, year)
        var countries = ""
        if (countriesList != null) {
            for (item in countriesList) {
                countries += item.name
                if(countriesList.indexOf(item) < countriesList.size) countries += ", "
            }
        }
        broadcastIntent.putExtra(DETAILS_COUNTRIES_EXTRA, countries)
        var genres = ""
        if (genresList != null) {
            for (item in genresList) {
                genres += item.name
                if(genresList.indexOf(item) < genresList.size) genres += ", "
            }
        }
        broadcastIntent.putExtra(DETAILS_GENRES_EXTRA, genres)
        broadcastIntent.putExtra(DETAILS_OVERVIEW_EXTRA, overview)
        broadcastIntent.putExtra(DETAILS_IMAGE_EXTRA, image)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(DETAILS_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onErrorRequest(error: String) {
        putLoadResult(DETAILS_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyResponse() {
        putLoadResult(DETAILS_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(DETAILS_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(DETAILS_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(DETAILS_LOAD_RESULT_EXTRA, result)
    }
}
