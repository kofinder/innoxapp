package com.finderbar.innox.ui.designer.artwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.finderbar.innox.*
import com.finderbar.innox.databinding.FragmentCustomizeArtworkBinding
import com.finderbar.innox.utilities.ViewPagerAdapter
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class ArtWorkCustomizeFragment: Fragment(), FragCallBack {

    private lateinit var rootFrag: RootFragListener
    private lateinit var itemArtworkCallBack: ItemArtWorkCallBack

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCustomizeArtworkBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_customize_artwork, container , false)
        var rootView : View  = binding.root
        rootFrag = parentFragment as RootFragListener
        itemArtworkCallBack = activity as ItemArtWorkCallBack

        val adapter = ViewPagerAdapter(
            childFragmentManager
        )
        adapter.addFragment(ArtWorkCategoryFragment(), "ArtWork By Category")
        adapter.addFragment(ArtWorkDesignerFragment(), "ArtWork By Designer")
        binding.viewPager.adapter = adapter
        binding.tabhost.setupWithViewPager(binding.viewPager)

        return rootView
    }

    override fun fragListener(frag: Fragment) {
        rootFrag.onPressed(frag)
    }

    override fun backPressed() {
        rootFrag.onBackPressed()
    }

    companion object {
        const val TAG = "ArtWorkCustomizeFragment"
    }

}