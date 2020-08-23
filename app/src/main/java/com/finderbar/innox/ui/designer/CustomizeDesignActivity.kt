package com.finderbar.innox.ui.designer

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.R
import com.finderbar.innox.repository.Status
import com.finderbar.innox.viewmodel.BaseApiViewModel

class CustomizeDesignActivity: AppCompatActivity() {

    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.recycler_view) lateinit var recyclerView: RecyclerView
    @BindView(R.id.progress) lateinit var progress: ProgressBar

    private val baseApiVM: BaseApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customize_designer)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Customized Design Listing"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adaptor = CategoryExpendableAdaptor(applicationContext, arrayListOf())
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.adapter = adaptor

        baseApiVM.loadCategories().observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progress.visibility = View.GONE
                    adaptor.addAll(res.data?.categories!!) }
                Status.ERROR -> {
                    progress.visibility = View.GONE
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}