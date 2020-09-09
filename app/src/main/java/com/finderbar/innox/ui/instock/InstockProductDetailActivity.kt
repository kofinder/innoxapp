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
import com.finderbar.innox.viewmodel.BizApiViewModel
import java.util.*


class InstockProductDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivityInstockProductDetailBinding
    private val bizApiVM: BizApiViewModel by viewModels()

   private var currentPage = 0
   private var numberOfPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instock_product_detail)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Product Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.txtTitle.text = "Hoddies"
        binding.txtPrice.text = "20000ks"
        binding.txtDescription.text="Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged"

        bizApiVM.loadProduct("").observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                   print(res.data)
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })

        val colorAdaptor: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.item_dropdown, listOf("Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"))
        binding.dropdownColor.setAdapter(colorAdaptor)
        binding.dropdownColor.clearFocus()

        val sizeAdaptor: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.item_dropdown, listOf("Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"))
        binding.dropdownSize.setAdapter(sizeAdaptor)
        binding.dropdownSize.clearFocus()

        val hAdaptor = StableArrayAdapter(
            applicationContext, mutableListOf(
                "Android", "IPhone", "WindowsMobile", "Blackberry",
                "WebOS", "Ubuntu", "Windows7", "Max OS X"
            )
        )
        binding.lvItem.adapter = hAdaptor
        setListViewHeight(binding.lvItem)

        binding.vpLayout.adapter =
            ImageSlidePagerAdapter(
                applicationContext, mutableListOf(
                    "https://demonuts.com/Demonuts/SampleImages/W-03.JPG",
                    "https://demonuts.com/Demonuts/SampleImages/W-08.JPG",
                    "https://demonuts.com/Demonuts/SampleImages/W-10.JPG",
                    "https://demonuts.com/Demonuts/SampleImages/W-13.JPG",
                    "https://demonuts.com/Demonuts/SampleImages/W-17.JPG",
                    "https://demonuts.com/Demonuts/SampleImages/W-21.JPG"
                )
            )
        binding.btnIndicator.setViewPager(binding.vpLayout)
        val density = resources.displayMetrics.density
        binding.btnIndicator.radius = 5 * density


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


        binding.btnCart.setOnClickListener{
            val frag = AddToCartDialogFragment.newInstance("item?._id!!", "prefs.fullName", "prefs.avatar", "getCurrentTime()")
            frag.show(supportFragmentManager, AddToCartDialogFragment.TAG)
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
            if (i == 0) view.layoutParams = ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
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
