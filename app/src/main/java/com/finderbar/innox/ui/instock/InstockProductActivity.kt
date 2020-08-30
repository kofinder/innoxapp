package com.finderbar.innox.ui.instock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockProductBinding

class InstockProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstockProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_product)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "CreateDesign"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}