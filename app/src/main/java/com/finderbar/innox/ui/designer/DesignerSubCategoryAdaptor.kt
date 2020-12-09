package com.finderbar.innox.ui.designer

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemDesignerSubCategoryBinding
import com.finderbar.innox.repository.SubCategory
import com.finderbar.innox.utilities.loadLarge
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class DesignerSubCategoryAdaptor(private val arrayList: MutableList<SubCategory>, var onItemProductClick: ItemProductClick?) :
    RecyclerView.Adapter<DesignerSubCategoryAdaptor.SubCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDesignerSubCategoryBinding.inflate(inflater, parent, false)
        return SubCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int)  {
        val datum : SubCategory = arrayList[position]
        holder.itemView.setOnClickListener{
            onItemProductClick?.onItemClick(datum.id!!, position)
        }
        holder.txtTitle.text = datum.name
        holder.thumb.loadLarge(Uri.parse(datum.photoUrl))
    }

    inner class SubCategoryViewHolder(val binding: ItemDesignerSubCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtTitle: TextView = binding.txtTitle
        val thumb: ImageView = binding.thumb
    }

}
