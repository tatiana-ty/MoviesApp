package com.example.android2.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.android2.model.Film
import com.example.android2.viewModel.FilmViewModel
import com.example.android2.R
import com.example.android2.model.*
import kotlinx.android.synthetic.main.film_fragment.*

class FilmFragment : Fragment() {
    private lateinit var filmBundle: Film

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_DATA_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_EMPTY_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_EXTRA -> print(PROCESS_ERROR)
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_URL_MALFORMED_EXTRA -> TODO(PROCESS_ERROR)
                DETAILS_RESPONSE_SUCCESS_EXTRA -> renderData(
                    MovieDTO(
                        ResultsDTO(
                            intent.getStringExtra(DETAILS_OVERVIEW_EXTRA),
                            intent.getStringExtra(DETAILS_TITLE_EXTRA), 1
                        )
                    )
                )
                else -> TODO(PROCESS_ERROR)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            print("here1")
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver, IntentFilter(FILM_LIST_INTENT_FILTER))
            print("here2")
        }
    }

    override fun onDestroy() {
        context?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.film_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Film()
        getFilm()
//        val film = arguments?.getParcelable<Film>(
//            BUNDLE_EXTRA
//        )
//
//
//        film?.let { film ->
//            name.text = film.name
//            rating.text = film.rating
//            year.text = film.year
//            country.text = film.country
//            genre.text = film.genre
//        }
    }

    private fun getFilm() {
        context?.let {nonNullContext ->
            nonNullContext.startService(Intent(nonNullContext, FilmInfoService::class.java).apply {
                    putExtra(FILM_NAME_EXTRA, filmBundle.name) })
        }
    }

    private fun renderData(movieDTO: MovieDTO) {
        print("render data")
        val resultsDTO = movieDTO.results[0]
        val title = resultsDTO.title
        val overview = resultsDTO.overview
        if (title == TEMP_INVALID || overview == FEELS_LIKE_INVALID) {
            TODO(PROCESS_ERROR)
        } else {
            name.text = title
            description.text = overview

        }
    }

    companion object {
        const val BUNDLE_EXTRA = "film"

        fun newInstance(bundle: Bundle): FilmFragment {
            val fragment = FilmFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}