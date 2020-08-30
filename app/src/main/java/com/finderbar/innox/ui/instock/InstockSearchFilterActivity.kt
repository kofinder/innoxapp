package com.finderbar.innox.ui.instock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockSearchFilterBinding
import com.finderbar.innox.repository.Category
import com.finderbar.innox.repository.Status
import com.finderbar.innox.viewmodel.BaseApiViewModel
import java.text.NumberFormat
import java.util.*


class InstockSearchFilterActivity:  AppCompatActivity() {

    private lateinit var binding: ActivityInstockSearchFilterBinding
    private val baseApiVM: BaseApiViewModel by viewModels()

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

        val categoryArrayAdaptor = CategoryArrayAdaptor(applicationContext, R.layout.item_dropdown, mutableListOf())
        categoryArrayAdaptor.setDropDownViewResource(R.layout.item_dropdown)
        binding.acCategory.clearFocus();
        binding.acCategory.setAdapter(categoryArrayAdaptor)


        baseApiVM.loadCategories().observe(this, androidx.lifecycle.Observer { res ->
            when(res.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    categoryArrayAdaptor.addAll(res.data?.categories!!)
                }
                Status.ERROR -> {}
            }
        })

        binding.acCategory.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                val user: Category = categoryArrayAdaptor.getItem(position)!!
                Toast.makeText(
                    applicationContext, "ID: " + user.id.toString() + "\nName: " + user.name,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(adapter: AdapterView<*>?) {}
        }

        binding.btnSearch.setOnClickListener{startActivity(Intent(this, InstockSearchActivity::class.java))}
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}