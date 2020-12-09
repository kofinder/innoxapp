package com.finderbar.innox.ui.instock

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockSearchBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.ui.instock.adaptor.SearchProductAdaptor
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class InStockSearchActivity:  AppCompatActivity(), ItemProductClick {

    private lateinit var binding: ActivityInstockSearchBinding
    private val bizApiVM: BizApiViewModel by viewModels()
    private var cartText : TextView? = null

    private var keyWord: String = ""
    private var categoryId = 0
    private var categoryName: String = "All"
    private var subCategoryId  = 0;
    private var subCategoryName: String? = "All"
    private var startPrice = 0
    private var endPrice = 50000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_search)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        keyWord = intent.getStringExtra("keyWord")
        categoryName = intent.getStringExtra("categoryName")
        subCategoryName = intent.getStringExtra("subCategoryName")
        categoryId = intent.getIntExtra("categoryId", 0)
        subCategoryId = intent.getIntExtra("subCategoryId", 0)
        startPrice = intent.getIntExtra("startPrice", 0)
        endPrice = intent.getIntExtra("endPrice", 0)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(categoryName.isNullOrBlank()) {
            supportActionBar?.title = "All"
        } else {
            supportActionBar?.title = categoryName
        }


        val adaptor = SearchProductAdaptor(arrayListOf(), this)
        binding.recyclerView.addItemDecoration(SpaceItemDecoration(10));
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2);
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());

        if (subCategoryName.isNullOrBlank().and(keyWord.isNullOrBlank())) {
            binding.btnText.text = "All"
        } else if(keyWord.isNotBlank()) {
            binding.btnText.text = keyWord
        } else {
            binding.btnText.text = subCategoryName
        }

        bizApiVM.loadSearchProduct(keyWord, startPrice, endPrice, categoryId, subCategoryId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    adaptor.addAll(res.data?.products!!)
                }
                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                }
            }
        })

        binding.btnFilter.setOnClickListener {
            val intent = Intent(this, InStockSearchFilterActivity::class.java)
            intent.putExtra("keyWord", keyWord)
            intent.putExtra("categoryId", categoryId)
            intent.putExtra("categoryName", categoryName)
            intent.putExtra("subCategoryId", categoryId)
            intent.putExtra("subCategoryName", subCategoryName)
            intent.putExtra("startPrice", startPrice)
            intent.putExtra("endPrice", endPrice)
            startActivity(intent);
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        val item = menu.findItem(R.id.action_cart)
        cartText = item.actionView.findViewById(R.id.cart_badge)
        cartText?.text = prefs.shoppingCount.toString()
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

    override fun onResume() {
        cartText?.text = prefs.shoppingCount.toString()
        super.onResume()
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(_id: Int, position: Int) {
        val intent = Intent(this, InStockProductDetailActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("position", position)
        startActivity(intent)
    }
}