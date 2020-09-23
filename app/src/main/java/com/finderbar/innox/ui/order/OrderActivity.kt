package com.finderbar.innox.ui.order

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityOrderBinding
import com.finderbar.innox.viewmodel.BizApiViewModel

class OrderActivity: AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        supportActionBar?.title = "User Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}