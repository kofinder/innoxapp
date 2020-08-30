package com.finderbar.innox.ui.designer

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityCustomizeCreateBinding
import com.finderbar.innox.repository.Status

import com.finderbar.innox.viewmodel.BaseApiViewModel

class CustomizeCreateDesignActivity: AppCompatActivity() {

    private val baseApiVM: BaseApiViewModel by viewModels()
    private lateinit var binding: ActivityCustomizeCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customize_create)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Create Design"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val productId: Int = intent?.extras?.get("productId") as Int

        baseApiVM.loadDesignerProduct(productId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print("loading")
                }
                Status.SUCCESS -> {
                    print(res.data?.customItems)
                }
                Status.ERROR -> {
                    print("err")
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}