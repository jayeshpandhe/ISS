package com.example.iss.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iss.R
import com.example.iss.viewmodel.AstronautsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Astronauts list UI
 */
@AndroidEntryPoint
class AstronautsListFragment : Fragment() {
    companion object {
        const val TAG = "AstronautsListFragment"
    }

    private val astronautsViewModel: AstronautsViewModel by lazy {
        ViewModelProvider(this)[AstronautsViewModel::class.java]
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.astronauts_list, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
        )
        attachObservers()
        astronautsViewModel.getAstronauts()
    }

    private fun attachObservers() {
        astronautsViewModel.astronautsLiveData.observe(viewLifecycleOwner) { issAstronauts ->
            recyclerView.adapter = AstronautsListAdapter(issAstronauts.people)
        }
    }
}