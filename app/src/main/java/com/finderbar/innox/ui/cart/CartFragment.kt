package com.finderbar.innox.ui.cart

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.finderbar.innox.AppContext
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentCartBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.ui.checkout.ProductCheckoutActivity
import com.finderbar.innox.viewmodel.BizApiViewModel
import java.util.ArrayList

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val bizApiVM: BizApiViewModel by viewModels()
    private val carts: MutableList<Int> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val context = activity as AppCompatActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        val adaptor = CartAdaptor(context, mutableListOf());

        bizApiVM.loadShoppingCart().observe(viewLifecycleOwner, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.lblHeader.visibility = View.GONE
                    binding.lblCheckout.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    res.data?.let {
                        binding.progress.visibility = View.GONE
                        binding.lblHeader.visibility = View.VISIBLE
                        binding.lblCheckout.visibility = View.VISIBLE
                        adaptor.addAll(it.carts!!)
                        binding.lvItem.adapter = adaptor
                        binding.txtTotal.text = it.totalAmountText
                    } ?: run {
                        binding.progress.visibility = View.GONE
                        binding.lblHeader.visibility = View.GONE
                        binding.lblCheckout.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.lblHeader.visibility = View.GONE
                    binding.lblCheckout.visibility = View.GONE
                }
            }
        })

        binding.cbSelectAll.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked) {
                adaptor.selectAll()
            } else {
                adaptor.unSelectAll()
            }
        }

        binding.btnDelete.setOnClickListener{
            var arr = adaptor.getCheckItem();
            print(arr)
        }

        binding.btnCheckout.setOnClickListener{
            var cartIds = adaptor.getCheckItem();
            val chIntent = Intent(AppContext, ProductCheckoutActivity::class.java)
            chIntent.putIntegerArrayListExtra("cartIds", ArrayList(cartIds))
            startActivity(chIntent)
        }

        return binding.root
    }

}