package com.finderbar.innox.ui.designer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentDesignerBinding
import com.finderbar.innox.repository.Status
import com.finderbar.innox.viewmodel.BaseApiViewModel
import com.finderbar.innox.utilities.SpaceItemDecoration


class DesignerFragment : Fragment() {

    private val baseApiVM: BaseApiViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDesignerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_designer, container , false)
        var rootView : View  = binding.root

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

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration( SpaceItemDecoration(20) );
        binding.recyclerView.adapter = adaptor

        baseApiVM.loadCategories().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    adaptor.addAll(res.data?.categories!!)
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })

        return rootView
    }
}
