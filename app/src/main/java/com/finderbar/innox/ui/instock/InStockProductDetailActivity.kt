package com.finderbar.innox.ui.instock

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityInstockProductDetailBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.repository.Color
import com.finderbar.innox.repository.Size
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.ui.instock.adaptor.ColorArrayAdaptor
import com.finderbar.innox.ui.instock.adaptor.ImageSlidePagerAdapter
import com.finderbar.innox.ui.instock.adaptor.SizeArrayAdaptor
import com.finderbar.innox.viewmodel.BizApiViewModel
import es.dmoral.toasty.Toasty
import java.util.*

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
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
    private var priceText: String? = ""
    private var price: Int? = 0
    private lateinit var cartText : TextView

    @SuppressLint("ResourceAsColor")
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
                    binding.txtPrice.text = product.priceText
                    binding.txtDescription.text = HtmlCompat.fromHtml(
                        product.description!!,
                        HtmlCompat.FROM_HTML_MODE_COMPACT
                    )

                    binding.vpLayout.adapter = ImageSlidePagerAdapter(
                        applicationContext,
                        product.images!!
                    )
                    binding.btnIndicator.setViewPager(binding.vpLayout)
                    val density = resources.displayMetrics.density
                    binding.btnIndicator.radius = 5 * density

                    val colorAdaptor = ColorArrayAdaptor(
                        applicationContext,
                        R.layout.item_dropdown,
                        product.colors!!
                    )
                    colorAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownColor.clearFocus()
                    binding.dropdownColor.setAdapter(colorAdaptor)
                    binding.dropdownColor.setOnItemClickListener { parent, view, position, id ->
                        colorId = (parent.getItemAtPosition(position) as Color).id
                        colorName = (parent.getItemAtPosition(position) as Color).name
                    }

                    val sizeAdaptor = SizeArrayAdaptor(
                        applicationContext,
                        R.layout.item_dropdown,
                        product.sizes!!
                    )
                    sizeAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownSize.clearFocus()
                    binding.dropdownSize.setAdapter(sizeAdaptor)
                    binding.dropdownSize.setOnItemClickListener { parent, _, position, _ ->
                        sizeId = (parent.getItemAtPosition(position) as Size).id
                        sizeName = (parent.getItemAtPosition(position) as Size).name
                    }

                    productName = product.name
                    priceText = product.priceText
                    price = product.price
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })

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

        if(prefs.userId.isNullOrBlank()) {
            binding.btnCart.visibility = View.GONE
        } else {
            binding.btnCart.visibility = View.VISIBLE
        }

        binding.btnCart.setOnClickListener {
            val frag = AddToCartDialogFragment.newInstance(
                productId,
                colorId!!,
                sizeId!!,
                productName!!,
                colorName!!,
                sizeName!!,
                price!!,
                priceText!!
            )
            frag.show(supportFragmentManager, AddToCartDialogFragment.TAG)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        val item = menu.findItem(R.id.action_cart)
        cartText = item.actionView.findViewById(R.id.cart_badge)
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

    fun setShoppingCartToCount(count: Int) {
        cartText.text = count.toString()
    }

}