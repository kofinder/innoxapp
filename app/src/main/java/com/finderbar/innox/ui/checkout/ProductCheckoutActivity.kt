package com.finderbar.innox.ui.checkout

import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityProductCheckoutBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.viewmodel.BizApiViewModel

class ProductCheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductCheckoutBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_checkout)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Checkout"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.txtPrice.text = "20000ks"

        bizApiVM.loadProduct("").observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    print(res.data)
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })

        val colorAdaptor: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.item_dropdown, listOf("Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"))
        binding.dropdownState.setAdapter(colorAdaptor)
        binding.dropdownState.clearFocus()

        val sizeAdaptor: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.item_dropdown, listOf("Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"))
        binding.dropdownTownship.setAdapter(sizeAdaptor)
        binding.dropdownTownship.clearFocus()

        binding.btnConfirm.setOnClickListener{
            val frag = ConfirmOrderFragment.newInstance()
            frag.show(supportFragmentManager, ConfirmOrderFragment.TAG)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}