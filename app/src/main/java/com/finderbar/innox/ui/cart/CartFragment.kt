package com.finderbar.innox.ui.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.finderbar.innox.AppContext
import com.finderbar.innox.ItemCartCallBack
import com.finderbar.innox.R
import com.finderbar.innox.databinding.FragmentCartBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.repository.CartIds
import com.finderbar.innox.ui.checkout.ProductCheckoutActivity
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty
import java.util.*

class CartFragment : Fragment(), ItemCartCallBack {

    private lateinit var binding: FragmentCartBinding
    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var acProgress: ACProgressFlower
    private lateinit var adaptor: CartAdaptor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity as AppCompatActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)
        acProgress = ACProgressFlower.Builder(requireContext())
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(android.graphics.Color.WHITE)
            .text("Please Wait")
            .fadeColor(android.graphics.Color.DKGRAY).build();

        adaptor = CartAdaptor(context, mutableListOf(), this);
        binding.lvItem.adapter = adaptor

        binding.cbSelectAll.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked) {
                adaptor.selectAll()
            } else {
                adaptor.unSelectAll()
            }
        }

        binding.btnDelete.setOnClickListener{
            var arr = adaptor.getCheckItem();
            bizApiVM.loadDeleteShoppingCart(CartIds(arr)).observe(
                viewLifecycleOwner,
                Observer { res ->
                    when (res.status) {
                        Status.LOADING -> {
                            acProgress.show()
                        }
                        Status.SUCCESS -> {
                            acProgress.hide()
                            if (res.data?.totalAmount!! < 1) {
                                binding.lblError.visibility = View.VISIBLE
                                binding.lblCard.visibility = View.GONE
                                binding.progress.visibility = View.GONE
                                binding.lblHeader.visibility = View.GONE
                                binding.lblCheckout.visibility = View.GONE
                            } else {
                                adaptor.modifyArray(res.data?.carts!!)
                                binding.txtTotal.text = res.data?.totalAmountText
                            }

                            val arrays = prefs.cartIds!!
                            arrays.removeAll(arrays)
                            res.data.carts?.forEach { x ->
                                arrays.add(x.productId.toString())
                            }
                            prefs.cartIds = arrays
                            prefs.shoppingCount = arrays.size

                            binding.cbSelectAll.isChecked = false
                        }
                        Status.ERROR -> {
                            acProgress.hide()
                            Toasty.error(requireContext(), res.msg.toString()).show()
                        }
                    }
                })
        }

        binding.btnCheckout.setOnClickListener{
            var cartIds = adaptor.getCheckItem();
            var productIds = adaptor.getProductIds();
            val chIntent = Intent(AppContext, ProductCheckoutActivity::class.java)
            chIntent.putIntegerArrayListExtra("cartIds", ArrayList(cartIds))
            chIntent.putIntegerArrayListExtra("productIds", ArrayList(productIds))
            startActivity(chIntent)
        }

        loadItem()

        binding.btnRefresh.setOnClickListener {
            loadItem()
        }

        return binding.root
    }

    private fun loadItem() {
        bizApiVM.loadShoppingCart().observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    binding.lblError.visibility = View.GONE
                    binding.lblCard.visibility = View.GONE
                    binding.progress.visibility = View.VISIBLE
                    binding.lblHeader.visibility = View.GONE
                    binding.lblCheckout.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    res.data?.let {
                        if (it.carts.isNullOrEmpty()) {
                            binding.lblError.visibility = View.VISIBLE
                            binding.lblCard.visibility = View.GONE
                            binding.progress.visibility = View.GONE
                            binding.lblHeader.visibility = View.GONE
                            binding.lblCheckout.visibility = View.GONE
                        } else {
                            binding.lblError.visibility = View.GONE
                            binding.lblCard.visibility = View.VISIBLE
                            binding.progress.visibility = View.GONE
                            binding.lblHeader.visibility = View.VISIBLE
                            binding.lblCheckout.visibility = View.VISIBLE
                            adaptor.addAll(it.carts)
                            binding.txtTotal.text = it.totalAmountText
                        }
                    } ?: run {
                        binding.lblError.visibility = View.VISIBLE
                        binding.lblCard.visibility = View.GONE
                        binding.progress.visibility = View.GONE
                        binding.lblHeader.visibility = View.GONE
                        binding.lblCheckout.visibility = View.GONE
                    }
                }
                Status.ERROR -> {
                    binding.lblError.visibility = View.VISIBLE
                    binding.lblCard.visibility = View.GONE
                    binding.progress.visibility = View.GONE
                    binding.lblHeader.visibility = View.GONE
                    binding.lblCheckout.visibility = View.GONE
                }
            }
        })
    }


    override fun onItemClick(carId: Int, quantity: Int) {
        bizApiVM.loadEditShoppingCart(carId, quantity).observe(viewLifecycleOwner, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    acProgress.show()
                }
                Status.SUCCESS -> {
                    acProgress.hide()
                    adaptor.modifyArray(res.data?.carts!!)
                    binding.txtTotal.text = res.data?.totalAmountText
                }
                Status.ERROR -> {
                    acProgress.hide()
                    Toasty.error(requireContext(), res.msg.toString()).show()
                }
            }
        })
    }

}

