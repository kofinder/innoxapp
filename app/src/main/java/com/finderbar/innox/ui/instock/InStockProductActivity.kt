package com.finderbar.innox.ui.instock

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.finderbar.innox.ItemSubCategoryProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockProductBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.ui.instock.adaptor.InStockProductSubCategoryAdaptor
import com.finderbar.innox.viewmodel.BizApiViewModel


class InStockProductActivity : AppCompatActivity(), ItemSubCategoryProductClick {
    private lateinit var binding: ActivityInstockProductBinding
    private val bizApiVM: BizApiViewModel by viewModels()
    private var categoryId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_product)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "InStock Product"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        categoryId = intent.getIntExtra("categoryId", 0)

        bizApiVM.loadSubCategories(categoryId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    binding.rvSubCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    binding.rvSubCategory.setHasFixedSize(true);
                    binding.rvSubCategory.adapter = InStockProductSubCategoryAdaptor(res.data?.subCategories!!, this)
                    binding.rvSubCategory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(_id: Int) {
        print("message")
    }
}