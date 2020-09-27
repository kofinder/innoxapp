package com.finderbar.innox.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentCartBinding
import com.finderbar.innox.viewmodel.BizApiViewModel

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        bizApiVM.loadShoppingCart().observe(viewLifecycleOwner, Observer { res ->
            print(res.data)
        })

        return binding.root
    }
}