package com.example.android2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class Adapter(private val cellClickListener: CellClickListener) : RecyclerView.Adapter<Adapter.EnrollmentsViewHolder>() {
    private val items: List<String>

    init {
        this.items = Arrays.asList("1", "2", "3", "4", "5", "6")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnrollmentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false)

        return EnrollmentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EnrollmentsViewHolder, position: Int) {
        holder.bind(getItem(position))
        val data = items[position]

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener()
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    fun getItem(position: Int): String {
        return items[position]
    }

    class EnrollmentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(value: String) {
//            itemView.titleTextView.text = value
        }

    }

}