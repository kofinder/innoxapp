package com.finderbar.innox.ui.instock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.R

class InstockSearchActivity:  AppCompatActivity() {
    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instock_search)

        ButterKnife.bind(this)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "CreateDesign"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}