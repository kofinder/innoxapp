package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityLoginBinding
import com.finderbar.innox.viewmodel.BizApiViewModel

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnLogin.setOnClickListener{startActivity(Intent(this, MainActivity::class.java))}
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}