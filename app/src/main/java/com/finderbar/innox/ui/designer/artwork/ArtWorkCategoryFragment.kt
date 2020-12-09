package com.finderbar.innox.ui.designer.artwork

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
import com.finderbar.innox.*
import com.finderbar.innox.databinding.FragmentArtworkCategoryBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class ArtWorkCategoryFragment: Fragment(), ItemArtWorkTitleCallBack {

    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var fragCallBack: FragCallBack
    private lateinit var itemArtworkCallBack: ItemArtWorkCallBack

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentArtworkCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_artwork_category, container , false)
        var rootView : View = binding.root

        fragCallBack = parentFragment as FragCallBack
        itemArtworkCallBack = activity as ItemArtWorkCallBack

        bizApiVM.loadArtWorkCategory().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    val adaptor = ArtWorkCategoryAdaptor(res.data?.artWorkCategories!!, this)
                    binding.recyclerView.addItemDecoration(SpaceItemDecoration(10));
                    binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2);
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

    override fun onItemClick(_id: Int, title: String) {
        val frag =
            CategoryArtWorkFragment.newInstance(
                _id,
                title
            );
        fragCallBack.fragListener(frag)
    }
}