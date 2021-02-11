package com.example.android2.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.Adapter
import com.example.android2.CellClickListener
import com.example.android2.FilmFragment
import com.example.android2.R


class MainFragment : Fragment(), CellClickListener {

    lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater!!.inflate(R.layout.main_fragment, container, false)
        loadView(view)
        recyclerView = view.findViewById(R.id.filmGrid)
        //filmItem.setOnClickListener( { v -> openFilmDescription()})
        return view
    }


    @SuppressLint("ResourceType")
    override fun onCellClickListener() {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment1, FilmFragment())
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

    fun loadView(view: View) {
        var recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.filmGrid) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = Adapter(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //val observer = Observer<Any> { renderData(it) }
        //viewModel.getData().observe(viewLifecycleOwner, observer)
    }

//
//    private fun renderData(data: Any) {
//        Toast.makeText(context, "data", Toast.LENGTH_LONG).show()
//    }


}
