package com.example.android2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.android2.*
import com.example.android2.model.*
import com.example.android2.repository.MoviesAPI
import com.example.android2.repository.ServiceBuilder
import com.example.android2.viewModel.AppState
import com.example.android2.viewModel.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.mainView
import kotlinx.android.synthetic.main.movie_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val request = ServiceBuilder.buildService(MoviesAPI::class.java)
        val call = request.getListOfMovies("popular", MOVIES_API_KEY)

        call.enqueue(object : Callback<MoviesListDTO>{
            override fun onResponse(call: Call<MoviesListDTO>, response: Response<MoviesListDTO>) {
                if (response.isSuccessful){
                    mainFragmentLoadingLayout.visibility = View.GONE
                    mainFragmentRecyclerView.apply {
                        layoutManager = GridLayoutManager(context, 3)
                        adapter = MainFragmentAdapter(object : OnItemClickListener {
                            override fun onItemClick(movieId: Int) {
                                val manager = activity?.supportFragmentManager
                                if (manager != null) {
                                    val bundle = Bundle().apply {
                                        putInt(MovieFragment.BUNDLE_EXTRA, movieId)
                                    }
                                    manager.beginTransaction()
                                        .add(R.id.container, MovieFragment.newInstance(bundle))
                                        .addToBackStack("")
                                        .commitAllowingStateLoss()
                                }
                            }
                        }, response.body()!!.results!!)
                    }
                }
            }
            override fun onFailure(call: Call<MoviesListDTO>, t: Throwable) {
                t.printStackTrace()
            }
        })

        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })

        viewModel.getMovieFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                //adapter.setMovie(appState.movieData)
            }
            is AppState.Loading -> {
                mainFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                mainFragmentLoadingLayout.visibility = View.GONE
                mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getMovieFromLocalSource() }
                )
            }
        }
    }

    override fun onDestroy() {
        //adapter.removeListener()
        super.onDestroy()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}
