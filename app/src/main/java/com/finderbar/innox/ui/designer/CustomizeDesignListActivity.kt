package com.finderbar.innox.ui.designer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityCustomizeDesignerBinding
import com.finderbar.innox.repository.Status
import com.finderbar.innox.viewmodel.BaseApiViewModel

class CustomizeDesignListActivity: AppCompatActivity(), ItemProductClick {

    private val baseApiVM: BaseApiViewModel by viewModels()
    private lateinit var binding: ActivityCustomizeDesignerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customize_designer)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Customized Design Listing"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adaptor = CategoryExpendableAdaptor(applicationContext, arrayListOf(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.adapter = adaptor

        baseApiVM.loadCategories().observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    adaptor.addAll(res.data?.categories!!)
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
        val intent= Intent(this,CustomizeDesignProductActivity::class.java)
        intent.putExtra("subCategoryId", _id)
        startActivity(intent)
    }
}