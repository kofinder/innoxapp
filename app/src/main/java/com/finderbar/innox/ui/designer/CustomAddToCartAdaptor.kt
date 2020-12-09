package com.finderbar.innox.ui.designer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ItemCustomAddToCartBinding
import com.finderbar.innox.repository.CustomSize


/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */

class CustomAddToCartAdaptor(val context: Context, private val arrays: MutableList<CustomSize>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding: ItemCustomAddToCartBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val datum = arrays[position]
        binding = DataBindingUtil.inflate(inflater, R.layout.item_custom_add_to_cart, parent, false)
        binding.checkbox.text = datum.name
        return binding.root;
    }

    override fun getItem(position: Int): Any = arrays[position]

    override fun getItemId(position: Int): Long = position.toLong();

    override fun getCount(): Int = arrays.size

}