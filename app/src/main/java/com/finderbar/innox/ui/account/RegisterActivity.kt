package com.finderbar.innox.ui.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityRegisterBinding

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Register"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var ft : FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.ft_main, RegisterFragment() )
        ft.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}