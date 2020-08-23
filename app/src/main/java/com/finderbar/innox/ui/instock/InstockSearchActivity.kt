package com.finderbar.innox.ui.instock

import android.os.Bundle
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class InstockSearchActivity:  AppCompatActivity() {

    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.ac_category) lateinit var categoryDropdown: AutoCompleteTextView
    @BindView(R.id.ac_sub_category) lateinit var subCategoryDropdown: AutoCompleteTextView
    @BindView(R.id.keywords) lateinit var keyword: TextInputLayout
    @BindView(R.id.btn_search) lateinit var btnSearch: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instock_search)
        ButterKnife.bind(this)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Filter"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}