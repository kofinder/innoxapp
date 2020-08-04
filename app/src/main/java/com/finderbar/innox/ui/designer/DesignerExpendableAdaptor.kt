package com.finderbar.innox.ui.designer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter

class DesignerExpendableAdaptor(private val context: Context, private val header: MutableList<String>, private val body: HashMap<String, MutableList<String>>): BaseExpandableListAdapter() {


    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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