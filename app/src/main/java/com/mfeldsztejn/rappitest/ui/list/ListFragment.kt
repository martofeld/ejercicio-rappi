package com.mfeldsztejn.rappitest.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfeldsztejn.rappitest.R
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    companion object {
        private const val SORT_BY_KEY = "SORT_BY"

        fun newInstance(sorter: String): ListFragment {
            val fragment = ListFragment()

            val arguments = Bundle()
            arguments.putString(SORT_BY_KEY, sorter)
            fragment.arguments = arguments
            return fragment
        }
    }

    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: MoviesAdapter
    @VisibleForTesting
    lateinit var sortBy: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        if (arguments != null)
            sortBy = arguments!!.getString(SORT_BY_KEY)!! // Should always be there

        viewModel.discover(sortBy)

        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressbar.visibility = View.VISIBLE

        adapter = MoviesAdapter()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        listenForChanges()
    }

    private fun listenForChanges() {
        viewModel.moviesPagedList.observe(this, Observer {
            adapter.submitList(it)
            progressbar.visibility = View.GONE
        })
    }

    fun search(query: String?) {
        if (viewModel.search(sortBy, query))
            listenForChanges()
    }

    fun clearSearch() {
        viewModel.discover(sortBy)
        listenForChanges()
    }
}
