package com.finderbar.innox.ui.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.finderbar.innox.R

class LoginActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}