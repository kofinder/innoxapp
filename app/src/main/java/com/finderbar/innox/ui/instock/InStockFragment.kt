package com.finderbar.innox.ui.instock

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentInstockBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.viewmodel.BizApiViewModel

class InStockFragment : Fragment() {

    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentInstockBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_instock, container , false)
        var rootView : View  = binding.root

        val adaptor = InStockCategoryAdaptor(requireContext(), arrayListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.adapter = adaptor

        bizApiVM.loadCategories().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.btnSearch.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    res.data?.let {
                        binding.progress.visibility = View.GONE
                        binding.btnSearch.visibility = View.VISIBLE
                        adaptor.addAll(it.categories!!)
                    } ?: run {
                        binding.progress.visibility = View.GONE
                        binding.btnSearch.visibility = View.VISIBLE
                    }
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.btnSearch.visibility = View.GONE
                }
            }
        })

        binding.btnSearch.setOnClickListener{startActivity(Intent(activity, InStockSearchFilterActivity::class.java))}

        return rootView
    }
}