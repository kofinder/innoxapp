package com.finderbar.innox.ui.instock

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TabHost
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockProductBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.ui.instock.adaptor.InStockProductAdaptor
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.finderbar.jovian.utilities.android.loadAvatar
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.tab_indicator.view.*
import org.w3c.dom.Text


class InStockProductActivity : AppCompatActivity(), ItemProductClick, TabHost.TabContentFactory {

    private lateinit var binding: ActivityInstockProductBinding
    private lateinit var productAdaptor: InStockProductAdaptor
    private val bizApiVM: BizApiViewModel by viewModels()
    private var cartText : TextView? = null
    private var categoryId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_product)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "InStock Product"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        categoryId = intent.getIntExtra("categoryId", 0)

        val adaptor = InStockProductAdaptor(arrayListOf(), this)
        binding.recyclerView.addItemDecoration(SpaceItemDecoration(10));
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2);
        binding.recyclerView.adapter = adaptor
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());

        bizApiVM.loadSubCategories(categoryId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    val datum = res.data?.subCategories
                    datum?.forEach { product ->
                        binding.tabHost.setup()
                        binding.tabHost.tabWidget.orientation = LinearLayout.VERTICAL
                        binding.tabHost.addTab(
                            binding.tabHost.newTabSpec(product.name!!).setIndicator(
                                createIndicatorView(
                                    binding.tabHost,
                                    product.id!!,
                                    product.name,
                                    product.photoUrl!!
                                )
                            ).setContent(this)
                        )
                    }
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })


        binding.tabHost.setOnTabChangedListener {
            adaptor.clear()
            val currentIdx: Int = binding.tabHost.currentTab
            val tabWidget = binding.tabs[currentIdx]
            val textView: TextView = tabWidget.findViewById(R.id.txt_id)
            val tabCount: Int = binding.tabHost.tabWidget.childCount
            val id = textView.text.toString();

            for (x in 0 until tabCount) {
                val indicator: RelativeLayout = binding.tabs[x].findViewById<View>(R.id.tab_indicator) as RelativeLayout
                val divider: View = binding.tabs[x].findViewById(R.id.side_view)
                if(x == currentIdx) {
                    divider.visibility = View.VISIBLE
                    indicator.setBackgroundColor(Color.WHITE)
                } else{
                    divider.visibility = View.GONE
                    indicator.setBackgroundColor(resources.getColor(R.color.colorGray))
                }
            }

            bizApiVM.loadAllSubCategoryProduct(id.toInt(), 1).observe(this, Observer { res ->
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
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        cartText?.text = prefs.shoppingCount.toString()
        super.onResume()
    }

    override fun onItemClick(_id: Int, position: Int) {
        val intent = Intent(this, InStockProductDetailActivity::class.java)
        intent.putExtra("_id", _id)
        intent.putExtra("position", position)
        startActivity(intent)
    }

    private fun createIndicatorView(tabHost: TabHost, productId: Int, label: CharSequence, imageUrl: String): View? {
        val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val tabIndicator: View = inflater.inflate(
            R.layout.tab_indicator,
            tabHost.tabWidget,
            false
        )

        tabIndicator.civ_thumb.loadAvatar(Uri.parse(imageUrl))
        tabIndicator.title.text = label
        tabIndicator.txt_id.text = productId.toString()
        tabIndicator.side_view.visibility = View.GONE
        return tabIndicator
    }

    override fun createTabContent(tag: String?): View {
        return TextView(this)
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


}