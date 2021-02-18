package com.example.android2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.model.CellClickListener
import com.example.android2.model.Film
import com.example.android2.R
import com.example.android2.model.getFilmsList
import kotlinx.android.synthetic.main.film_item.view.*

class MainFragmentAdapter(private var cellClickListener: CellClickListener?) : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {
    private var data = getFilmsList();

    fun setWeather(data: List<Film>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.film_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(film: Film) {
            itemView.filmName.text = film.name
            //itemView.text = film.name
            itemView.setOnClickListener {
                cellClickListener?.onCellClickListener(film)
            }

        }
    }

    fun removeListener() {
        cellClickListener = null
    }

}