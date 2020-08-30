package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityRegisterInfoBinding
import com.finderbar.innox.viewmodel.BaseApiViewModel

class RegisterInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterInfoBinding
    private val baseApiVM: BaseApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_info)
        binding.btnContinue.setOnClickListener{startActivity(Intent(this, RegisterInfoActivity::class.java))}
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}