package com.finderbar.innox.ui.designer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.finderbar.innox.R
import com.finderbar.innox.ui.home.HomeViewModel
import com.finderbar.innox.utilities.SpaceItemDecoration


class DesignerFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var unkinder: Unbinder

    @BindView(R.id.recycler_view) lateinit var recyclerReview: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_designer, container, false)
        unkinder = ButterKnife.bind(this, root)

        val adaptor = DesignerAdaptor(arrayListOf())
        val layoutManager = GridLayoutManager(requireContext(), 2);
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 3 == 0)
                    2
                else
                    1
            }
        }

        recyclerReview.layoutManager = layoutManager
        recyclerReview.addItemDecoration(
            SpaceItemDecoration(
                20
            )
        );
        recyclerReview.adapter = adaptor

        homeViewModel.result.observe(viewLifecycleOwner, Observer {
            adaptor.addAll(it)
        })

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        unkinder.unbind()
    }

}
