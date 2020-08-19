package com.finderbar.innox.ui.product

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.R
import com.finderbar.innox.repository.Status
import com.finderbar.innox.viewmodel.HomeViewModel
import com.google.android.material.button.MaterialButton
import com.viewpagerindicator.CirclePageIndicator
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class ProductDetailActivity: AppCompatActivity() {

    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.txt_colors) lateinit var dropDownColor: AutoCompleteTextView
    @BindView(R.id.txt_sizes) lateinit var dropDownSize: AutoCompleteTextView
    @BindView(R.id.list_item) lateinit var highlightView: ListView
    @BindView(R.id.txt_description) lateinit var txtDescription: TextView
    @BindView(R.id.txt_title) lateinit var txtTitle: TextView
    @BindView(R.id.txt_price) lateinit var txtPrice: TextView
    @BindView(R.id.img_next) lateinit var btnNext: CircleImageView
    @BindView(R.id.img_prev) lateinit var btnPrev: CircleImageView
    @BindView(R.id.btn_cart) lateinit var btnCart: MaterialButton
    @BindView(R.id.indicator) lateinit var btnIndicator: CirclePageIndicator
    @BindView(R.id.vp_slider_layout) lateinit var sliderLayout: ViewPager

    private val homeVM: HomeViewModel by viewModels()

   private var currentPage = 0
   private var numberOfPages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        ButterKnife.bind(this)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Product Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        txtTitle.text = "Hoddies"
        txtPrice.text = "20000ks"
        txtDescription.text="Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged"

        homeVM.loadProduct("").observe(this, Observer { res ->
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
        dropDownColor.setAdapter(colorAdaptor)
        dropDownColor.clearFocus()

        val sizeAdaptor: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.item_dropdown, listOf("Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"))
        dropDownSize.setAdapter(sizeAdaptor)
        dropDownSize.clearFocus()

        val hAdaptor = StableArrayAdapter(applicationContext,  mutableListOf("Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X")
        )
        highlightView.adapter = hAdaptor
        setListViewHeight(highlightView)

        sliderLayout.adapter = ImageSlidePagerAdapter(applicationContext, mutableListOf("https://demonuts.com/Demonuts/SampleImages/W-03.JPG", "https://demonuts.com/Demonuts/SampleImages/W-08.JPG", "https://demonuts.com/Demonuts/SampleImages/W-10.JPG",
            "https://demonuts.com/Demonuts/SampleImages/W-13.JPG", "https://demonuts.com/Demonuts/SampleImages/W-17.JPG", "https://demonuts.com/Demonuts/SampleImages/W-21.JPG")
        )
        btnIndicator.setViewPager(sliderLayout)
        val density = resources.displayMetrics.density
        btnIndicator.radius = 5 * density


        val handler = Handler()
        val update = Runnable {
            if (currentPage == numberOfPages) {
                currentPage = 0
            }
            sliderLayout.setCurrentItem(currentPage++, true)
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 3000, 3000)

        btnIndicator.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                currentPage = position
            }
            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(pos: Int) {}
        })


        btnCart.setOnClickListener{
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
            if (i == 0) view.setLayoutParams(ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            )
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
