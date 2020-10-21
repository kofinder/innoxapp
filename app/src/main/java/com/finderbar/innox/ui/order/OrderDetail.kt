package com.finderbar.innox.ui.order

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finderbar.innox.AppContext
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ActivityOrderDetailBinding
import com.finderbar.innox.network.Status
import com.finderbar.innox.repository.OrderHistoryDetail
import com.finderbar.innox.ui.checkout.CheckOutAdaptor
import com.finderbar.innox.utilities.OrderHistoryType.*
import com.finderbar.innox.viewmodel.BizApiViewModel

class OrderDetail: AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    private val bizApiVM: BizApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.title = "Order Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val orderId = intent.getIntExtra("orderId", 0)
        val orderDate = intent.getStringExtra("orderDate")

        when(intent.getStringExtra("orderType")) {
            ACTIVE.name -> {
                binding.txtOrderInfo.text = resources.getString(R.string.order_active_info)
                binding.ivThumb.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.order_success))
            }
            PENDING.name -> {
                binding.txtOrderInfo.text = resources.getString(R.string.order_pending_info)
                binding.ivThumb.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.order_pending_large))
            }
            PAST.name -> {
                binding.txtOrderInfo.text = resources.getString(R.string.order_past_info, "02/02/2020")
                binding.ivThumb.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.order_past_large))
            }
        }

        bizApiVM.loadOrderHistoryId(orderId).observe(this, Observer { res ->
            when(res.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {
                    val datum: OrderHistoryDetail = res.data!!
                    binding.txtInvoice.text = "#"+ datum.invoiceNumber
                    binding.txtOrderDate.text = orderDate
                    binding.txtOrderStatus.text = datum.orderStatusText
                    binding.txtOrderDeliverCost.text = datum.deliveryFeeText
                    binding.txtTotalStatus.text = datum.totalCostText
                    binding.listItem.adapter = CheckOutAdaptor(this, datum.orderItem!!);
                }
                Status.ERROR -> {

                }
            }

        })

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