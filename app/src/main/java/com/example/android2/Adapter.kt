package com.example.android2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.film_item.view.*
import java.util.*

class Adapter(private var cellClickListener: CellClickListener?) : RecyclerView.Adapter<Adapter.MainViewHolder>() {
//    private val items: List<String>

    private var data: List<Film> = listOf(
        Film("Film1", "4.5", "2020", "US", "Comedy"),
        Film("Film2", "4.0", "2010", "Russia", "Horror"),
        Film("Film3", "4.8", "2015", "France", "Comedy"),
        Film("Film4", "3.8", "2018", "US", "Drama"),
        Film("Film5", "3.6", "2012", "GB", "Action"),
        Film("Film6", "4.4", "2021", "Germany", "Romance")
    )

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


//    init {
//        this.items = Arrays.asList("1", "2", "3", "4", "5", "6")
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnrollmentsViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)
//
//        return EnrollmentsViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: EnrollmentsViewHolder, position: Int) {
//        holder.bind(getItem(position))
//        val data = items[position]
//
//        holder.itemView.setOnClickListener {
//            cellClickListener.onCellClickListener()
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return 6
//    }
//
//    fun getItem(position: Int): String {
//        return items[position]
//    }
//
//    class EnrollmentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        fun bind(value: String) {
////            itemView.titleTextView.text = value
//        }
//
//    }

}