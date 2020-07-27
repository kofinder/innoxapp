package com.finderbar.innox.ui.designer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.felipecsl.asymmetricgridview.library.Utils
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter
import com.finderbar.innox.R
import com.finderbar.innox.model.ImageItem
import com.finderbar.innox.ui.home.HomeViewModel


class DesignerFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val items: MutableList<ImageItem> = arrayListOf()
    var currentOffset = 0;

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_designer, container, false)
        val adaptor = DesignerAdaptor(requireContext())
        val recyclerView: AsymmetricGridView = root.findViewById(R.id.recycler_view)
        val asymmetricAdapter = AsymmetricGridViewAdapter<ImageItem>(requireContext(), recyclerView, adaptor)
        recyclerView.requestedHorizontalSpacing = Utils.dpToPx(requireContext(), 3F)
        recyclerView.setRequestedColumnCount(3)

        recyclerView.adapter = asymmetricAdapter
        homeViewModel.result.observe(viewLifecycleOwner, Observer { result ->
            result.forEachIndexed { index, product ->
                var colSpan: Int = if (index % 6 == 0 || index % 6 == 5) {
                    2
                } else {
                    1
                }
                val rowSpan = colSpan
                items.add(ImageItem(product.url, colSpan, rowSpan, currentOffset + index ))
            }
            currentOffset += result.size;
            adaptor.arrayList = items
        })

        return root
    }

}
