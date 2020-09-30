package com.finderbar.innox.ui.cart

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ItemCartBinding
import com.finderbar.innox.repository.Cart
import com.finderbar.jovian.utilities.android.loadAvatar


class CartAdaptor(val context: Context, private val arrays: MutableList<Cart>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding: ItemCartBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val datum = arrays[position]
        binding = DataBindingUtil.inflate(inflater, R.layout.item_cart, parent, false)
        binding.txtTitle.text = datum.name
        binding.txtCount.text = datum.quantity.toString()
        binding.txtUnitPrice.text = datum.priceText
        binding.txtSubTotal.text = datum.subTotalText
        binding.imgProduct.loadAvatar(Uri.parse(datum.image))
        binding.checkbox.isChecked = datum.isCheck

        binding.checkbox.setOnCheckedChangeListener {_, isCheck ->
            datum.isCheck = isCheck
            this.notifyDataSetChanged()
        }

        return binding.root;
    }

    override fun getItem(position: Int): Any = arrays[position]

    override fun getItemId(position: Int): Long = position.toLong();

    override fun getCount(): Int = arrays.size

    fun addAll(arr: MutableList<Cart>) {
        arrays.addAll(arr)
        this.notifyDataSetChanged()
    }

    fun selectAll() {
        for (arr in arrays) {
            arr.isCheck = !false;
        }
        this.notifyDataSetChanged()
    }

    fun unSelectAll() {
        for (arr in arrays) {
            arr.isCheck = false;
        }
        this.notifyDataSetChanged()
    }

    fun getCheckItem(): MutableList<Int> {
        val checkArrays: MutableList<Int> = mutableListOf()
        for (arr in arrays) {
            if(arr.isCheck) {
                checkArrays.add(arr.id!!)
            }
        }
        return checkArrays;
    }

}
