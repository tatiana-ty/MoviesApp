package com.example.android2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android2.*
import com.example.android2.model.CellClickListener
import com.example.android2.model.Film
import com.example.android2.viewModel.MainViewModel

class MainFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainViewModel

    private val adapter = MainFragmentAdapter(object :
        CellClickListener {
        override fun onCellClickListener(film: Film) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(FilmFragment.BUNDLE_EXTRA, film)
                manager.beginTransaction()
                    .add(R.id.container, FilmFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        loadView(view)
        recyclerView = view.findViewById(R.id.filmGrid)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //val observer = Observer<Any> { renderData(it) }
        //viewModel.getData().observe(viewLifecycleOwner, observer)
    }

    fun loadView(view: View) {
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.filmGrid) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }


    override fun onDestroy() {
        adapter.removeListener()
        super.onDestroy()
    }
}
