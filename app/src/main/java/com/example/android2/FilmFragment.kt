package com.example.android2

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.film_fragment.*

class FilmFragment : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "film"

        fun newInstance(bundle: Bundle): FilmFragment {
            val fragment = FilmFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: FilmViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.film_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
        film?.let { film ->
            name.text = film.name
            rating.text = film.rating
            year.text = film.year
            country.text = film.country
            genre.text = film.genre
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FilmViewModel::class.java)
        // TODO: Use the ViewModel
    }

}