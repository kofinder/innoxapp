package com.finderbar.innox.ui.designer

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemExpendableCategoryBinding
import com.finderbar.innox.repository.Category
import com.finderbar.innox.utilities.ExpandableLayout
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.jovian.utilities.android.loadAvatar

class DesignerCategoryExpendableAdaptor(private val context: Context, private val arrayList: MutableList<Category>, var onItemClick: ItemProductClick) : RecyclerView.Adapter<DesignerCategoryExpendableAdaptor.CategoryExpendableViewHolder>() {

    inner class CategoryExpendableViewHolder(val binding: ItemExpendableCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    private val expandedPositionSet: HashSet<Int> = HashSet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryExpendableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExpendableCategoryBinding.inflate(inflater, parent, false)
        return CategoryExpendableViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: CategoryExpendableViewHolder, position: Int)  {
        val datum : Category = arrayList[position]
        holder.binding.txtTitle.text = datum.name
        holder.binding.thumb.loadAvatar(Uri.parse(datum.photoUrl))

        holder.binding.recyclerView.adapter = DesignerSubCategoryAdaptor(datum.subCategory!!, onItemClick)
        holder.binding.recyclerView.addItemDecoration(SpaceItemDecoration(10));
        holder.binding.recyclerView.layoutManager = GridLayoutManager(context, 2);
        holder.binding.recyclerView.setHasFixedSize(true)
        holder.binding.recyclerView.isNestedScrollingEnabled = false
        holder.binding.recyclerView.itemAnimator = DefaultItemAnimator()
        holder.binding.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());

        holder.binding.expandLayout.setOnExpandListener(object :
            ExpandableLayout.OnExpandListener {
            override fun onExpand(expanded: Boolean) {
                if (expandedPositionSet.contains(position)) {
                    expandedPositionSet.remove(position)
                } else {
                    expandedPositionSet.add(position)
                }
            }
        })
        holder.binding.expandLayout.setExpand(expandedPositionSet.contains(position))
    }

    private fun add(r: Category) {
        arrayList.add(r)
        notifyItemInserted(arrayList.size - 1)                                                                                                                                                                      
    }

    fun addAll(resultList: List<Category>) {
        for (result in resultList) {
            add(result)
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
    }

}