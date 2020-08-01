package com.finderbar.innox.ui.designer

import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.finderbar.innox.R
import com.finderbar.innox.model.ImageItem
import com.finderbar.jovian.utilities.android.loadLarge

class DesignerAdaptor (private val context: Context): BaseAdapter() {
    var arrayList: MutableList<ImageItem> = arrayListOf();

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.item_designer_product,null)
        view.setBackgroundColor(view.resources.getColor(R.color.colorPrimaryDark))
        val thumb: ImageView = view.findViewById(R.id.thumb)
        thumb.loadLarge(Uri.parse(arrayList[position].urls))
        return view
    }

    override fun getItem(position: Int): Any = arrayList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = arrayList.size

}

