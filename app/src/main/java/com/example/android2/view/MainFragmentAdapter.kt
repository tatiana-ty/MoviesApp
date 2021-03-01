package com.example.android2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android2.model.OnItemClickListener
import com.example.android2.R
import com.example.android2.model.ResultsDTO

class MainFragmentAdapter(private var onItemClickListener: OnItemClickListener?,
                          val movies: List<ResultsDTO>) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    var moviesList = movies

    fun removeListener() {
        onItemClickListener = null
    }

    fun setMovies(movies: List<ResultsDTO>){
        moviesList = movies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_item, parent, false) as View
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        return holder.bind(moviesList[position])
    }

    inner class MainViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        private val photo: ImageView = itemView.findViewById(R.id.movieMainImage)
        private val title: TextView = itemView.findViewById(R.id.movieName)

        fun bind(movie: ResultsDTO) {
            val movieId = movie.id
            itemView.apply {
                setOnClickListener { onItemClickListener?.onItemClick(movieId!!) }
            }
            Glide.with(itemView.context).load("http://image.tmdb.org/t/p/w500${movie.image}").into(photo)
            title.text = movie.title
        }
    }

}