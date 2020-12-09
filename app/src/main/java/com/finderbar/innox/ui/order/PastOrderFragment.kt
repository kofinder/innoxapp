package com.finderbar.innox.ui.order

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
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.AppContext
import com.finderbar.innox.ItemOrderClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentOrderPastBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.utilities.OrderHistoryType

import com.finderbar.innox.viewmodel.BizApiViewModel
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class PastOrderFragment : Fragment(), ItemOrderClick {

    private lateinit var binding: FragmentOrderPastBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_past, container, false)

        val adaptor = OrderHistoryAdaptor(requireContext(), arrayListOf(), OrderHistoryType.PAST, this)
        val layoutManager = LinearLayoutManager(requireContext());
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext());
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.setHasFixedSize(true)

        bizApiVM.loadOrderHistory(3).observe(viewLifecycleOwner, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.pbLoading.visibility = View.GONE
                    val adaptor = OrderHistoryAdaptor(requireContext(), res.data?.orderHistories!!, OrderHistoryType.PAST, this)
                    val layoutManager = LinearLayoutManager(requireContext());
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    binding.recyclerView.layoutManager = LinearLayoutManager(requireContext());
                    binding.recyclerView.adapter = adaptor
                    binding.recyclerView.setHasFixedSize(true)
                    binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());
                }
                Status.ERROR -> {
                    binding.pbLoading.visibility = View.GONE
                }
            }
        })


        return binding.root;
    }

    override fun onItemClick(_id: Int, orderDate: String) {
        val intent = Intent(AppContext, OrderDetail::class.java)
        intent.putExtra("orderId", _id)
        intent.putExtra("orderType", OrderHistoryType.PAST.name)
        intent.putExtra("orderDate", orderDate)
        startActivity(intent)
    }
}
