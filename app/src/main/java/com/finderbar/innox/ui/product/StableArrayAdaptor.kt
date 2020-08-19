package com.finderbar.innox.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.finderbar.innox.R

class StableArrayAdapter(context: Context, private val words: MutableList<String>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): Any = words[position]

    override fun getCount(): Int  = words.size

    override fun getItemId(position: Int): Long  = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_listview, parent, false)
        val label: TextView = rowView.findViewById(R.id.label)
        label.text = words[position]
        return rowView
    }
}

