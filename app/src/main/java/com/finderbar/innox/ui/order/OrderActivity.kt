package com.finderbar.innox.ui.order

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityOrderBinding
import com.finderbar.innox.utilities.ViewPagerAdapter
import com.finderbar.innox.viewmodel.BizApiViewModel

class OrderActivity: AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Order"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(ActiveOrderFragment(), "Active Orders")
        adapter.addFragment(PendingOrderFragment(), "Pending Orders")
        adapter.addFragment(PastOrderFragment(), "Past Orders")
        binding.viewPager.adapter = adapter
        binding.tabHost.setupWithViewPager(binding.viewPager)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}