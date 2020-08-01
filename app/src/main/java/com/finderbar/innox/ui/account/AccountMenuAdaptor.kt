package com.finderbar.innox.ui.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.finderbar.innox.R

class AccountMenuAdaptor(private val context: Context, private val arrayList: ArrayList<String> ) : BaseAdapter() {
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.item_account_menu,null)
        val menu: TextView = view.findViewById(R.id.txt_menu)
        menu.text = arrayList[position]
        return view
    }

    override fun getItem(position: Int): Any = arrayList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = arrayList.size

}

