package com.finderbar.innox.ui.designer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentArtworkDesignerBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel

class ArtWorkDesignerFragment: Fragment(), ItemProductClick {
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentArtworkDesignerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_artwork_designer, container , false)
        var rootView : View = binding.root

        bizApiVM.loadArtWorkDesigner(4).observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    val adaptor = ArtWorkDesignerAdaptor(res.data?.artWorks!!, this)
                    binding.recyclerView.addItemDecoration(SpaceItemDecoration(10));
                    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 4);
                    binding.recyclerView.adapter = adaptor
                    binding.recyclerView.isNestedScrollingEnabled = false
                    binding.recyclerView.itemAnimator = DefaultItemAnimator()
                    binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })


        return rootView;
    }

    override fun onItemClick(_id: Int, position: Int) {

    }

}