package com.finderbar.innox.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentOrderPendingBinding

import com.finderbar.innox.viewmodel.BizApiViewModel

class PendingOrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderPendingBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_pending, container, false)
        return binding.root;
    }
}
