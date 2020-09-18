package com.finderbar.innox.ui.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityRegisterBinding
import com.finderbar.innox.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        replaceFragment(RegisterFragment())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}