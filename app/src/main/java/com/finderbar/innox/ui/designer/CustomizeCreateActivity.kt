package com.finderbar.innox.ui.designer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.R

class CustomizeCreateActivity: AppCompatActivity() {
    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize_create)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "CreateDesign"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}