package com.example.android2.view

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.android2.R
import com.example.android2.model.*
import com.example.android2.viewModel.AppState
import com.example.android2.viewModel.MovieViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.movie_fragment.*

class MovieFragment : Fragment() {
    private val viewModel: MovieViewModel by lazy { ViewModelProvider(this).get(MovieViewModel::class.java) }
    private var movieId: Int = 0
    private var noteText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val image = view.findViewById<ImageView>(R.id.stared)
        image.setOnClickListener(){
            image.setImageResource(R.drawable.stared_yes)
        }
        val buttonOk = view.findViewById<Button>(R.id.buttonOk)
        buttonOk.setOnClickListener(){
            val note = view.findViewById<TextInputEditText>(R.id.note)
            noteText = note.text.toString()

        }
        movieId = arguments?.getInt(BUNDLE_EXTRA)!!
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getMovieFromRemoteSource(movieId.toString())
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                mainView.visibility = View.VISIBLE
                loadingLayout.visibility = View.GONE
                setMovie(appState.movieData[0])
            }
            is AppState.Loading -> {
                mainView.visibility = View.GONE
                loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainView.visibility = View.VISIBLE
                loadingLayout.visibility = View.GONE
                mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {
                        viewModel.getMovieFromRemoteSource(
                            movieId.toString()
                        )
                    })
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setMovie(movie: Movie) {
        saveMovie(movie)
        movieTitle.text = movie.title
        rating.text = "Rating: " + movie.rating.toString()
        year.text = "Year: " + movie.year.split("-")[0]
        country.text = "Country: " + movie.country
        genre.text = "Genre: " + movie.genre
        description.text = movie.description
        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500/${movie.image}")
            .fitCenter()
            .into(movieImage)

    }

    private fun saveMovie(movie: Movie) {
        movie.note = noteText
        viewModel.saveMovieToDB(movie)
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): MovieFragment {
            val fragment = MovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}