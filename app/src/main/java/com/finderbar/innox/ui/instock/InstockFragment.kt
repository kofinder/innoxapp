package com.finderbar.innox.ui.instock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.R
import com.finderbar.innox.viewmodel.HomeViewModel

class InstockFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_instock, container, false)

        val productAdaptor: InstockProductAdaptor = InstockProductAdaptor(arrayListOf())
        val firstRecycleView: RecyclerView = root.findViewById(R.id.first_recycler_view)
        firstRecycleView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
        firstRecycleView.adapter = productAdaptor

        val secodRecycleView: RecyclerView = root.findViewById(R.id.second_recycler_view)
        secodRecycleView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
        secodRecycleView.adapter = productAdaptor

        val thirdRecycleView: RecyclerView = root.findViewById(R.id.third_recycler_view)
        thirdRecycleView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
        thirdRecycleView.adapter = productAdaptor

        val fourRecycleView: RecyclerView = root.findViewById(R.id.four_recycler_view)
        fourRecycleView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
        fourRecycleView.adapter = productAdaptor

        val fiveRecycleView: RecyclerView = root.findViewById(R.id.five_recycler_view)
        fiveRecycleView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, true)
        fiveRecycleView.adapter = productAdaptor

        homeViewModel.result.observe(viewLifecycleOwner, Observer {
            productAdaptor.addAll(it)
        })

        return root;
    }
}