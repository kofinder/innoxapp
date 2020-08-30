package com.finderbar.innox.ui.instock

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemInstockSubCategoryBinding
import com.finderbar.innox.repository.SubCategory
import com.finderbar.jovian.utilities.android.loadLarge

class InstockSubCategoryAdaptor(private val arrayList: MutableList<SubCategory>, var onItemProductClick: ItemProductClick?) : RecyclerView.Adapter<InstockSubCategoryAdaptor.SubCategoryInstockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryInstockViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstockSubCategoryBinding.inflate(inflater, parent, false)
        return SubCategoryInstockViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: SubCategoryInstockViewHolder, position: Int)  {
        val datum : SubCategory = arrayList[position]
        holder.itemView.setOnClickListener{
            onItemProductClick?.onItemClick(datum.id!!, position)
        }
        holder.txtTitle.text = datum.name
        holder.thumb.loadLarge(Uri.parse(datum.photoUrl))
    }

    inner class SubCategoryInstockViewHolder(val binding: ItemInstockSubCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtTitle: TextView = binding.txtTitle
        val thumb: ImageView = binding.thumb
    }

}
