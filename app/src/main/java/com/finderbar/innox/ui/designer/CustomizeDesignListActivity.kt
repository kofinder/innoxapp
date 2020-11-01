package com.finderbar.innox.ui.designer

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityCustomizeDesignerBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty

class CustomizeDesignListActivity: AppCompatActivity(), ItemProductClick {

    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var binding: ActivityCustomizeDesignerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customize_designer)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Customized Design Listing"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adaptor = DesignerCategoryExpendableAdaptor(applicationContext, arrayListOf(), this)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.adapter = adaptor

        bizApiVM.loadCategories().observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    adaptor.addAll(res.data?.categories!!)
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        val item = menu.findItem(R.id.action_cart)
        var cartText: TextView = item.actionView.findViewById(R.id.cart_badge)
        cartText.text = prefs.shoppingCount.toString()
        item.actionView.setOnClickListener{
            onOptionsItemSelected(item)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_cart) {
            if (!prefs.userId.isNullOrBlank()) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("tab", 3)
                startActivity(intent)
            } else {
                Toasty.warning(this, "You are not Login!").show()
            }

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(_id: Int, position: Int) {
        print("hello world")
        val intent= Intent(this, CustomizeDesignProductActivity::class.java)
        intent.putExtra("subCategoryId", _id)
        startActivity(intent)
    }
}