package com.finderbar.innox.ui.designer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityCustomizeCreateBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.ui.designer.artwork.CustomizeArtWorkDialogFragment
import com.finderbar.innox.ui.designer.fontstyle.CustomizeTextDialogFragment

import com.finderbar.innox.viewmodel.BizApiViewModel

class CustomizeCreateDesignActivity: AppCompatActivity(), ItemProductClick {

    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var binding: ActivityCustomizeCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customize_create)
        val productId: Int = intent?.extras?.get("productId") as Int

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Create Design"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bizApiVM.loadDesignerProduct(productId).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print("loading")
                }
                Status.SUCCESS -> {
                    supportActionBar?.title = res.data?.name
                    binding.txtPrice.text = res.data?.priceText
                    val adaptor = ButtonGroupAdaptor(res.data?.customItems?.get(0)!!.customLayout!!, this)
                    binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                    binding.recyclerView.setHasFixedSize(true);
                    binding.recyclerView.adapter = adaptor
                    binding.recyclerView.itemAnimator = DefaultItemAnimator()
                }
                Status.ERROR -> {
                    print("err")
                }
            }
        })


        binding.btnText.setOnClickListener{
            val frag = CustomizeTextDialogFragment.newInstance("item?._id!!", "prefs.fullName", "prefs.avatar", "getCurrentTime()")
            frag.show(supportFragmentManager, CustomizeTextDialogFragment.TAG)
        }
        binding.btnArtwork.setOnClickListener{
            val frag = CustomizeArtWorkDialogFragment.newInstance("item?._id!!", "prefs.fullName", "prefs.avatar", "getCurrentTime()")
            frag.show(supportFragmentManager, CustomizeArtWorkDialogFragment.TAG)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onItemClick(_id: Int, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}