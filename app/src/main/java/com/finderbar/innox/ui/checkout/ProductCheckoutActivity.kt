package com.finderbar.innox.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import cc.cloudist.acplibrary.ACProgressConstant
import cc.cloudist.acplibrary.ACProgressFlower
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityProductCheckoutBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.prefs
import com.finderbar.innox.repository.CartIds
import com.finderbar.innox.repository.ConfirmOrder
import com.finderbar.innox.repository.State
import com.finderbar.innox.repository.TownShip
import com.finderbar.innox.ui.MainActivity
import com.finderbar.innox.ui.account.StateArrayAdaptor
import com.finderbar.innox.ui.account.TownShipArrayAdaptor
import com.finderbar.innox.viewmodel.BizApiViewModel
import com.google.android.material.radiobutton.MaterialRadioButton
import es.dmoral.toasty.Toasty


class ProductCheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductCheckoutBinding
    private val bizApiVM: BizApiViewModel by viewModels()
    private lateinit var acProgress: ACProgressFlower

    private var stateId: Int? = 0
    private var townShipId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_checkout)
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Checkout"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val cartIds= intent?.getIntegerArrayListExtra("cartIds")
        acProgress = ACProgressFlower.Builder(this)
            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
            .themeColor(android.graphics.Color.WHITE)
            .text("Please Wait")
            .fadeColor(android.graphics.Color.DKGRAY).build();

        val adaptor = CheckOutAdaptor(this, mutableListOf());
        bizApiVM.loadState().observe(this, Observer { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    val stateAdaptor = StateArrayAdaptor(
                        this,
                        R.layout.item_dropdown,
                        res.data?.states!!
                    )
                    stateAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownState.clearFocus();
                    binding.dropdownState.setAdapter(stateAdaptor)
                    binding.dropdownState.setOnItemClickListener { parent, _, position, _ ->
                        stateId = (parent.getItemAtPosition(position) as State).id
                    }
                }
            }
        })

        bizApiVM.loadTownship(1).observe(this, Observer { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    val townshipAdaptor = TownShipArrayAdaptor(
                        this,
                        R.layout.item_dropdown,
                        res.data?.townships!!
                    )
                    townshipAdaptor.setDropDownViewResource(R.layout.item_dropdown)
                    binding.dropdownTownship.clearFocus();
                    binding.dropdownTownship.setAdapter(townshipAdaptor)
                    binding.dropdownTownship.setOnItemClickListener { parent, _, position, _ ->
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
                        adaptor.addAll(it.orderItem!!)
                        binding.listItem.adapter = adaptor
                        setListViewHeight(binding.listItem)

                        it.paymentType?.forEach { x ->
                            val radioButton = MaterialRadioButton(this)
                            radioButton.text = x.name
                            radioButton.id = x.id!!
                            val params = RadioGroup.LayoutParams(
                                RadioGroup.LayoutParams.WRAP_CONTENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT
                            )
                            binding.rdoGroup.addView(radioButton, params)
                        }

                    } ?: kotlin.run {
                    }
                }
                Status.ERROR -> {
                    acProgress.hide()
                }
            }
        })


        binding.btnConfirm.setOnClickListener{
            val address = binding.address.text.toString();
            val remark = binding.address.text.toString();
            bizApiVM.loadConfirmOrder(
                ConfirmOrder(
                    cartIds!!,
                    "COD",
                    stateId!!,
                    townShipId!!,
                    address,
                    remark
                )
            ).observe(this, Observer { res ->
                when (res.status) {
                    Status.LOADING -> {
                        acProgress.show()
                    }
                    Status.SUCCESS -> {
                        res.data?.let {
                            acProgress.hide()
                            prefs.shoppingCount -= cartIds.size
                            Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show();
                            val frag = ConfirmOrderFragment.newInstance(
                                ArrayList(it.orderItem!!),
                                it.invoiceNumber!!,
                                it.totalCostText!!,
                                it.deliveryFeeText!!
                            )
                            frag.show(supportFragmentManager, ConfirmOrderFragment.TAG)
                        } ?: kotlin.run {
                            acProgress.hide()
                            Toasty.warning(this, "Fail!", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                    Status.ERROR -> {
                        acProgress.hide()
                        Toasty.error(this, res.msg!!, Toast.LENGTH_SHORT, true).show();
                    }
                }
            })

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

}