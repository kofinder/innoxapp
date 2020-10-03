package com.finderbar.innox.ui.instock

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockSearchFilterBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.repository.Category
import com.finderbar.innox.repository.SubCategory
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.google.android.material.slider.RangeSlider
import kotlinx.android.synthetic.main.activity_instock_search_filter.*
import java.text.NumberFormat
import java.util.*


class InStockSearchFilterActivity:  AppCompatActivity() {

    private lateinit var binding: ActivityInstockSearchFilterBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    private var keyWord: String = ""
    private var categoryId = 0
    private var categoryName: String = ""
    private var subCategoryId  = 0;
    private var subCategoryName: String = ""
    private var startPrice = 0
    private var endPrice = 50000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_search_filter)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Filter"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.slider.setLabelFormatter { value: Float ->
            val format = NumberFormat.getCurrencyInstance()
            format.maximumFractionDigits = 0
            format.currency = Currency.getInstance("MMK")
            format.format(value.toDouble())
        }

        binding.slider.addOnChangeListener { _, _, _ ->
            startPrice = slider.values[0].toInt()
            endPrice = slider.values[1].toInt()
            binding.txtPrice.text = """${startPrice} MMK- ${endPrice} MMK"""
        }

        val subCategoryArrayAdaptor = SubCategoryArrayAdaptor(applicationContext, R.layout.item_dropdown, mutableListOf())
        subCategoryArrayAdaptor.setDropDownViewResource(R.layout.item_dropdown)
        binding.acSubCategory.clearFocus();
        binding.acSubCategory.setAdapter(subCategoryArrayAdaptor)
        binding.acSubCategory.setOnItemClickListener { parent, _, position, _ ->
            val subCategory = parent.getItemAtPosition(position) as SubCategory
            subCategoryId = subCategory.id!!
            subCategoryName = subCategory.name!!
        }

        val categoryArrayAdaptor = CategoryArrayAdaptor(applicationContext, R.layout.item_dropdown, mutableListOf())
        categoryArrayAdaptor.setDropDownViewResource(R.layout.item_dropdown)
        binding.acCategory.clearFocus();
        binding.acCategory.setAdapter(categoryArrayAdaptor)
        binding.acCategory.setOnItemClickListener { parent, _, position, _ ->
            val category = parent.getItemAtPosition(position) as Category
            categoryId = category.id!!
            categoryName = category.name!!
            subCategoryArrayAdaptor.addAll(category.subCategory!!)
        }

        bizApiVM.loadCategories().observe(this, Observer { res ->
            when(res.status) {
                Status.LOADING -> {
                    categoryArrayAdaptor.addAll(mutableListOf())
                }
                Status.SUCCESS -> {
                    categoryArrayAdaptor.addAll(res.data?.categories!!)
                }
                Status.ERROR -> {
                    categoryArrayAdaptor.addAll(mutableListOf())
                }
            }
        })

        binding.btnSearch.setOnClickListener{
            keyWord = binding.keywords.text.toString()
            val intent = Intent(this, InStockSearchActivity::class.java)
            intent.putExtra("keyWord", keyWord)
            intent.putExtra("categoryId", categoryId)
            intent.putExtra("categoryName", categoryName)
            intent.putExtra("subCategoryId", categoryId)
            intent.putExtra("subCategoryName", subCategoryName)
            intent.putExtra("startPrice", startPrice)
            intent.putExtra("endPrice", endPrice)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}