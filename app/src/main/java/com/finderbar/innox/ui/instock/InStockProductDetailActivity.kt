package com.finderbar.innox.ui.instock

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockProductDetailBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.repository.Color
import com.finderbar.innox.repository.ProductDetail
import com.finderbar.innox.repository.Size
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty
import java.util.*


class InStockProductDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityInstockProductDetailBinding
    private val bizApiVM: BizApiViewModel by viewModels()
    private var currentPage = 0
    private var numberOfPages = 0
    private var colorId: Int? = 0
    private var sizeId: Int? = 0
    private var productName: String? = ""
    private var colorName: String? = ""
    private var sizeName: String? = ""
    private var price: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_product_detail)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Product Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val productId: Int = intent?.extras?.get("_id") as Int

        bizApiVM.loadProduct(productId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    var product = res.data!!
                    binding.txtTitle.text = product.name
                    binding.txtPrice.text = product.price
                    binding.txtDescription.text = product.description
                    binding.vpLayout.adapter = ImageSlidePagerAdapter(applicationContext, product.images!!)
                    binding.btnIndicator.setViewPager(binding.vpLayout)
                    val density = resources.displayMetrics.density
                    binding.btnIndicator.radius = 5 * density

                    val colorAdaptor = ColorArrayAdaptor(applicationContext, R.layout.item_dropdown, product.colors!!)
                    colorAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownColor.clearFocus();
                    binding.dropdownColor.setAdapter(colorAdaptor)
                    binding.dropdownColor.setOnItemClickListener { parent, view, position, id ->
                        colorId = (parent.getItemAtPosition(position) as Color).id
                        colorName = (parent.getItemAtPosition(position) as Color).name
                    }

                    val sizeAdaptor = SizeArrayAdaptor(applicationContext, R.layout.item_dropdown, product.sizes!!)
                    sizeAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownSize.clearFocus();
                    binding.dropdownSize.setAdapter(sizeAdaptor)
                    binding.dropdownSize.setOnItemClickListener { parent, view, position, id ->
                        sizeId = (parent.getItemAtPosition(position) as Size).id
                        sizeName = (parent.getItemAtPosition(position) as Size).name
                    }

                    productName = product.name
                    price = product.price

                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })


        val hAdaptor = StableArrayAdapter(applicationContext, mutableListOf("test"))
        binding.lvItem.adapter = hAdaptor
        setListViewHeight(binding.lvItem)

        val handler = Handler()
        val update = Runnable {
            if (currentPage == numberOfPages) {
                currentPage = 0
            }
            binding.vpLayout.setCurrentItem(currentPage++, true)
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000, 3000)

        binding.btnIndicator.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(pos: Int) {}
        })


        binding.btnCart.setOnClickListener {
            if(prefs.userId.isNullOrBlank()) {
                val frag = AddToCartDialogFragment.newInstance(productId, colorId.let { 0 }, sizeId.let { 0 }, productName!!, colorName!!, sizeName!!, price!!)
                frag.show(supportFragmentManager, AddToCartDialogFragment.TAG)
            } else {
                Toasty.warning(this, "You are not login.").show();
            }
        }

    }


    private fun setListViewHeight(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        val desiredWidth =
            MeasureSpec.makeMeasureSpec(listView.width, MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0) view.layoutParams =
                ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}