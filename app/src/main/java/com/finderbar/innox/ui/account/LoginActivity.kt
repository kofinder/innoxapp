package com.finderbar.innox.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.R
import com.google.android.material.button.MaterialButton

class LoginActivity: AppCompatActivity() {

    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.btn_login) lateinit var loginButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Login"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loginButton.setOnClickListener{startActivity(Intent(this, MainActivity::class.java))}

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}