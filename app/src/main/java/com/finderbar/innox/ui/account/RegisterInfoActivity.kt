package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.R
import com.google.android.material.button.MaterialButton

class RegisterInfoActivity : AppCompatActivity() {

    @BindView(R.id.btn_continue) lateinit var registerButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_info)
        ButterKnife.bind(this)

        registerButton.setOnClickListener{startActivity(Intent(this, RegisterInfoActivity::class.java))}
    }
}