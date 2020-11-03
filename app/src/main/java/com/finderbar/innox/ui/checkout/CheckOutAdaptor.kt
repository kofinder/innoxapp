package com.finderbar.innox.ui.checkout

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ItemCheckoutBinding
import com.finderbar.innox.repository.OrderItem
import com.finderbar.innox.utilities.loadAvatar


class CheckOutAdaptor(val context: Context, private val arrays: MutableList<OrderItem>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding: ItemCheckoutBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val datum = arrays[position]
        binding = DataBindingUtil.inflate(inflater, R.layout.item_checkout, parent, false)
        binding.txtTitle.text = datum.name
        binding.txtCount.text = datum.quantity.toString()
        binding.txtUnitPrice.text = datum.unitPriceText
        binding.txtSubTotal.text = datum.unitPriceText
        binding.imgProduct.loadAvatar(Uri.parse(datum.image))
        return binding.root;
    }

    override fun getItem(position: Int): Any = arrays[position]

    override fun getItemId(position: Int): Long = position.toLong();

    override fun getCount(): Int = arrays.size

    fun addAll(arr: MutableList<OrderItem>) {
        arrays.addAll(arr)
        this.notifyDataSetChanged()
    }


}
