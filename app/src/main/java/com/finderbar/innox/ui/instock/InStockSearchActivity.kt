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
import com.finderbar.innox.network.Status
import com.finderbar.innox.ui.instock.adaptor.SearchProductAdaptor
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel

class InStockSearchActivity:  AppCompatActivity(), ItemProductClick {

    private lateinit var binding: ActivityInstockSearchBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    private var keyWord: String = ""
    private var categoryId = 0
    private var categoryName: String = "All"
    private var subCategoryId  = 0;
    private var subCategoryName: String? = "All"
    private var startPrice = 0
    private var endPrice = 50000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_search)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        keyWord = intent.getStringExtra("keyWord")
        categoryName = intent.getStringExtra("categoryName")
        subCategoryName = intent.getStringExtra("subCategoryName")
        categoryId = intent.getIntExtra("categoryId", 0)
        subCategoryId = intent.getIntExtra("subCategoryId", 0)
        startPrice = intent.getIntExtra("startPrice", 0)
        endPrice = intent.getIntExtra("endPrice", 0)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(categoryName.isNullOrBlank()) {
            supportActionBar?.title = "All"
        } else {
            supportActionBar?.title = categoryName
        }


        val adaptor = SearchProductAdaptor(arrayListOf(), this)
        binding.recyclerView.addItemDecoration(SpaceItemDecoration(10));
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2);
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());

        if (subCategoryName.isNullOrBlank().and(keyWord.isNullOrBlank())) {
            binding.btnText.text = "All"
        } else if(keyWord.isNotBlank()) {
            binding.btnText.text = keyWord
        } else {
            binding.btnText.text = subCategoryName
        }

        bizApiVM.loadSearchProduct(keyWord, startPrice, endPrice, categoryId, subCategoryId).observe(this, Observer { res ->
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

        binding.btnFilter.setOnClickListener {
            val intent = Intent(this, InStockSearchFilterActivity::class.java)
            intent.putExtra("keyWord", keyWord)
            intent.putExtra("categoryId", categoryId)
            intent.putExtra("categoryName", categoryName)
            intent.putExtra("subCategoryId", categoryId)
            intent.putExtra("subCategoryName", subCategoryName)
            intent.putExtra("startPrice", startPrice)
            intent.putExtra("endPrice", endPrice)
            startActivity(intent);
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(_id: Int, position: Int) {
        val intent = Intent(this, InStockProductDetailActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}