package com.finderbar.innox.ui.checkout

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityProductCheckoutBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.repository.CartIds
import com.finderbar.innox.repository.State
import com.finderbar.innox.repository.TownShip
import com.finderbar.innox.ui.account.StateArrayAdaptor
import com.finderbar.innox.ui.account.TownShipArrayAdaptor
import com.finderbar.innox.viewmodel.BizApiViewModel

class ProductCheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductCheckoutBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    private var stateId: Int? = 0
    private var townShipId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_checkout)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Checkout"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val cartIds=intent?.getIntegerArrayListExtra("cartIds")

        val adaptor = CheckOutAdaptor(this, mutableListOf());

        bizApiVM.loadState().observe(this, Observer { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    val stateAdaptor = StateArrayAdaptor(this, R.layout.item_dropdown, res.data?.states!!)
                    stateAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownState.clearFocus();
                    binding.dropdownState.setAdapter(stateAdaptor)
                    binding.dropdownState.setOnItemClickListener { parent, view, position, id ->
                        stateId = (parent.getItemAtPosition(position) as State).id
                    }
                }
            }
        })

        bizApiVM.loadTownship(1).observe(this, Observer { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    val townshipAdaptor = TownShipArrayAdaptor(this, R.layout.item_dropdown, res.data?.townships!!)
                    townshipAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownTownship.clearFocus();
                    binding.dropdownTownship.setAdapter(townshipAdaptor)
                    binding.dropdownTownship.setOnItemClickListener { parent, view, position, id ->
                        townShipId = (parent.getItemAtPosition(position) as TownShip).id
                    }
                }
            }
        })

        bizApiVM.loadOrderPreload(CartIds(cartIds!!)).observe(this, Observer { res ->
            when (res.status) {
                Status.LOADING -> {
                    print(res.status)
                }
                Status.SUCCESS -> {
                    res.data?.let {
                        binding.txtPrice.text = it.totalItemCostText
                        binding.txtCost.text = it.totalCostText
                        binding.txtDelivery.text = it.deliveryCost.toString()
                        binding.edName.text?.append(it.userDetail.name)
                        binding.edPhone.text?.append(it.userDetail.phoneNo)
                        stateId = it.userDetail.stateId
                        townShipId = it.userDetail.townShipId
                        adaptor.addAll(it.orderItem!!)
                        binding.listItem.adapter = adaptor
                        setListViewHeight(binding.listItem)
                    }?: kotlin.run {

                    }
                }
                Status.ERROR -> {
                    print(res.msg)
                }
            }
        })


        binding.btnConfirm.setOnClickListener{
            val frag = ConfirmOrderFragment.newInstance()
            frag.show(supportFragmentManager, ConfirmOrderFragment.TAG)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setListViewHeight(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        val desiredWidth =
            View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0) view.layoutParams =
                ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }
}