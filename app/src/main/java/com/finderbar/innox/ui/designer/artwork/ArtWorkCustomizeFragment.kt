package com.finderbar.innox.ui.designer.artwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.finderbar.innox.*
import com.finderbar.innox.databinding.FragmentCustomizeArtworkBinding
import com.finderbar.innox.utilities.ViewPagerAdapter

class ArtWorkCustomizeFragment: Fragment(), FragCallBack {

    private lateinit var rootFrag: RootFragListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentCustomizeArtworkBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_customize_artwork, container , false)
        var rootView : View  = binding.root
        rootFrag = parentFragment as RootFragListener

        val adapter = ViewPagerAdapter(
            childFragmentManager
        )
        adapter.addFragment(ArtWorkCategoryFragment(), "ArtWork By Category")
        adapter.addFragment(ArtWorkDesignerFragment(), "ArtWork By Designer")
        binding.viewPager.adapter = adapter
        binding.tabhost.setupWithViewPager(binding.viewPager)

        return rootView
    }

    companion object {
        const val TAG = "ArtWorkCustomizeFragment"
    }

    override fun fragListener(frag: Fragment) {
        rootFrag.onPressed(frag)
    }

    override fun backPressed() {
        rootFrag.onBackPressed()
    }

}