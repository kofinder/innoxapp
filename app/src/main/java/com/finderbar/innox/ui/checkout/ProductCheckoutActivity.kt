package com.finderbar.innox.ui.checkout

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.finderbar.innox.R
import com.finderbar.innox.repository.Status
import com.finderbar.innox.ui.product.AddToCartDialogFragment
import com.finderbar.innox.ui.product.ImageSlidePagerAdapter
import com.finderbar.innox.ui.product.StableArrayAdapter
import com.finderbar.innox.viewmodel.HomeViewModel
import com.google.android.material.button.MaterialButton
import com.viewpagerindicator.CirclePageIndicator
import de.hdodenhof.circleimageview.CircleImageView

class ProductCheckoutActivity : AppCompatActivity() {

    @BindView(R.id.main_toolbar) lateinit var toolbar: Toolbar


    @BindView(R.id.ac_state_division) lateinit var acDivision: AutoCompleteTextView
    @BindView(R.id.ac_township) lateinit var acTownship: AutoCompleteTextView
    @BindView(R.id.list_item) lateinit var highlightView: ListView
    //@BindView(R.id.txt_description) lateinit var txtDescription: TextView
   // @BindView(R.id.txt_title) lateinit var txtTitle: TextView
    @BindView(R.id.txt_price) lateinit var txtPrice: TextView
    @BindView(R.id.btn_confirm) lateinit var btnConfirm: MaterialButton

    private val homeVM: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_checkout)
        ButterKnife.bind(this)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Checkout"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //txtTitle.text = "Hoddies"
        txtPrice.text = "20000ks"
        //txtDescription.text="Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged"

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
        acDivision.setAdapter(colorAdaptor)
        acDivision.clearFocus()

        val sizeAdaptor: ArrayAdapter<String> = ArrayAdapter(applicationContext, R.layout.item_dropdown, listOf("Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"))
        acTownship.setAdapter(sizeAdaptor)
        acTownship.clearFocus()

//        val hAdaptor = StableArrayAdapter(applicationContext,  mutableListOf("Android","IPhone","WindowsMobile","Blackberry",
//            "WebOS","Ubuntu","Windows7","Max OS X")
//        )
//        highlightView.adapter = hAdaptor
//        setListViewHeight(highlightView)

        btnConfirm.setOnClickListener{
            val frag = ConfirmOrderFragment.newInstance()
            frag.show(supportFragmentManager, ConfirmOrderFragment.TAG)
        }

    }


    private fun setListViewHeight(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        val desiredWidth =
            View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0) view.setLayoutParams(
                ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            )
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
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