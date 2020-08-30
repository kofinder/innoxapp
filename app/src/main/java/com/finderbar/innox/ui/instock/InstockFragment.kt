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
import com.finderbar.innox.repository.Status
import com.finderbar.innox.viewmodel.BaseApiViewModel

class InstockFragment : Fragment() {

    private val baseApiVM: BaseApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentInstockBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_instock, container , false)
        var rootView : View  = binding.root

        val adaptor = InstockCategoryAdaptor(requireContext(), arrayListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.adapter = adaptor

        baseApiVM.loadCategories().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> { binding.progress.visibility = View.VISIBLE }
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    adaptor.addAll(res.data?.categories!!)
                }
                Status.ERROR -> { binding.progress.visibility = View.GONE }
            }
        })

        binding.btnSearch.setOnClickListener{startActivity(Intent(activity, InstockSearchFilterActivity::class.java))}

        return rootView
    }
}