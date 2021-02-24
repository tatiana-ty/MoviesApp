package com.example.android2.view

import android.app.IntentService
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.android2.model.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val FILM_NAME_EXTRA = "Film Name"
private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

class FilmInfoService(name: String = "FilmInfoService") : IntentService(name) {

    private val broadcastIntent = Intent(FILM_LIST_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        } else {
            val name = intent.getStringExtra(FILM_NAME_EXTRA)
            if (name == null) {
                onEmptyData()
            } else {
                loadFilmInfo(name)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadFilmInfo(name: String) {
        print("here")
        try {
            val uri =
                URL("https://api.themoviedb.org/3/trending/movie/week?api_key=${REQUEST_API_KEY}")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                }

                val movieDTO: MovieDTO =
                    Gson().fromJson(
                        getLines(BufferedReader(InputStreamReader(urlConnection.inputStream))),
                        MovieDTO::class.java
                    )
                print(movieDTO)
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
        val list = movieDTO.results
        list?.let {
            onSuccessResponse(it[0])
        }
    }

    private fun onSuccessResponse(result: ResultsDTO) {
        putLoadResult(DETAILS_RESPONSE_SUCCESS_EXTRA)
        broadcastIntent.putExtra(DETAILS_TITLE_EXTRA, result.title)
        broadcastIntent.putExtra(DETAILS_OVERVIEW_EXTRA, result.overview)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
//        filmList = mutableListOf()
//        for(i in list) {
//            var film = Film(name = i.title, description = i.overview, country = i.original_language)
//            (filmList as MutableList<Film>).add(film);
//        }
//        println(filmList)
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
