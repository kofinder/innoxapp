package com.finderbar.innox.ui.designer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityCustomizeProductBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel

class CustomizeDesignProductActivity: AppCompatActivity(), ItemProductClick {
    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var binding: ActivityCustomizeProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customize_product)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Customize T-shirts"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adaptor = CustomizeProductAdaptor(arrayListOf(), this)
        binding.recyclerView.addItemDecoration(SpaceItemDecoration(10));
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2);
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());

        val subCategoryId: Int = intent?.extras?.get("subCategoryId") as Int

        bizApiVM.loadCustomSubCategoryProduct(subCategoryId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    adaptor.addAll(res.data?.products!!)
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(_id: Int, position: Int) {
        val intent= Intent(this,CustomizeCreateDesignActivity::class.java)
        intent.putExtra("productId", _id)
        startActivity(intent)
    }
}