package com.finderbar.innox.ui.instock

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.finderbar.innox.R
import com.finderbar.innox.repository.Category



class CategoryArrayAdaptor(val mContext: Context, val textViewResourceId: Int, val arrayList: MutableList<Category>) : ArrayAdapter<Category>(mContext, 0, arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dropdownItemView = convertView

        if (dropdownItemView == null)
            dropdownItemView = LayoutInflater.from(mContext).inflate(textViewResourceId, parent,false)

        val datum = arrayList[position]
        val txtName: TextView = dropdownItemView!!.findViewById(R.id.txt_name)
        txtName.text = datum.name

        return dropdownItemView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = super.getDropDownView(position, convertView, parent)
        val txtName: TextView = view.findViewById(R.id.txt_name)
        txtName.text = arrayList[position].name
        return view;
    }

    private fun add(r: Category) {
        arrayList.add(r)
        notifyDataSetChanged()
    }

    fun addAll(resultList: MutableList<Category>) {
        for (result in resultList) {
            add(result)
        }
    }
}
