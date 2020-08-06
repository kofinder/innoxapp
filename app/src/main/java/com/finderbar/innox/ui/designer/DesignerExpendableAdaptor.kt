package com.finderbar.innox.ui.designer

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.finderbar.innox.R


class DesignerExpendableAdaptor(private val context: Context, private val header: MutableList<String>, private val body: HashMap<String, MutableList<String>>): BaseExpandableListAdapter() {


    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        val headerTitle = getGroup(groupPosition) as String
        if (convertView == null) {
            val infalInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.item_designer_list_group, null)
        }

        val lblListHeader = convertView?.findViewById(R.id.lblListHeader) as TextView
        lblListHeader.setTypeface(null, Typeface.BOLD)
        lblListHeader.text = headerTitle

        return convertView!!
    }


    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val childText = getChild(groupPosition, childPosition) as String
        var convertView = convertView
        if (convertView == null) {
            val infalInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.item_designer_list_item, null)
        }
        val txtListChild = convertView?.findViewById(R.id.lblListItem) as TextView
        txtListChild.text = childText
        return convertView!!
    }

    override fun getGroup(groupPosition: Int): Any  = header[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any  = body[header[groupPosition]]!![childPosition]

    override fun getGroupId(groupPosition: Int): Long  = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = childPosition.toLong()

    override fun getChildrenCount(groupPosition: Int): Int = body[header[groupPosition]]?.size!!

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean = true

    override fun hasStableIds(): Boolean = false

    override fun getGroupCount(): Int = header.size
}