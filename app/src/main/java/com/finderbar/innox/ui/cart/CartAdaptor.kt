package com.finderbar.innox.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.finderbar.innox.R
import com.finderbar.innox.databinding.ItemCartBinding
import com.finderbar.innox.repository.Cart


class CartAdaptor(private val context: Context, private val dataSource: MutableList<Cart>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding: ItemCartBinding


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_cart, parent, false)
        return binding.root;
    }

    override fun getItem(position: Int): Any = dataSource[position]

    override fun getItemId(position: Int): Long = position.toLong();

    override fun getCount(): Int = dataSource.size

}
