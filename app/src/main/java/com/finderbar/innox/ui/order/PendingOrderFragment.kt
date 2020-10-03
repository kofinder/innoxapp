package com.finderbar.innox.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentOrderPastBinding
import com.finderbar.innox.databinding.FragmentOrderPendingBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.utilities.OrderHistoryType

import com.finderbar.innox.viewmodel.BizApiViewModel

class PendingOrderFragment : Fragment(), ItemProductClick {

    private lateinit var binding: FragmentOrderPendingBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_pending, container, false)

        val adaptor = OrderHistoryAdaptor(requireContext(), arrayListOf(), OrderHistoryType.ACTIVE, this)
        val layoutManager = LinearLayoutManager(requireContext());
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext());
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.setHasFixedSize(true)

        bizApiVM.loadOrderHistory(1).observe(viewLifecycleOwner, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    res.data?.let {
                        binding.progress.visibility = View.VISIBLE
                       // adaptor.addAll(it.orderHistories!!)
                    }.run {
                        print("empty message")
                    }
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                }
            }
        })

        return binding.root;
    }

    override fun onItemClick(_id: Int, position: Int) {
        TODO("Not yet implemented")
    }
}
