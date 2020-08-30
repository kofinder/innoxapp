package com.finderbar.innox.ui.instock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockSearchBinding
import com.finderbar.innox.repository.Status
import com.finderbar.innox.ui.product.ProductDetailActivity
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BaseApiViewModel

class InstockSearchActivity:  AppCompatActivity(), ItemProductClick {

    private lateinit var binding: ActivityInstockSearchBinding
    private val baseApiVM: BaseApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_search)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Hoddies"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adaptor = SearchProductAdaptor(arrayListOf(), this)
        binding.recyclerView.addItemDecoration(SpaceItemDecoration(10));
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2);
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());

        baseApiVM.loadSearchProduct("Man", "0", "15000", "1", "1").observe(this, Observer { res ->
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
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}