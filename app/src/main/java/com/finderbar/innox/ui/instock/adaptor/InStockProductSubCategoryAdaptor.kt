package com.finderbar.innox.ui.instock.adaptor

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemSubCategoryProductClick
import com.finderbar.innox.databinding.ItemInstockProductSubCategoryBinding
import com.finderbar.innox.repository.SubCategory
import com.finderbar.jovian.utilities.android.loadAvatar

class InStockProductSubCategoryAdaptor(
    private val arrays: MutableList<SubCategory>,
    private val onItemSubCategoryProductClick: ItemSubCategoryProductClick
): RecyclerView.Adapter<InStockProductSubCategoryAdaptor.InStockProductSubCategoryViewHolder>() {
    class InStockProductSubCategoryViewHolder(val binding: ItemInstockProductSubCategoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InStockProductSubCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstockProductSubCategoryBinding.inflate(inflater, parent, false)
        return InStockProductSubCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InStockProductSubCategoryViewHolder, position: Int) {
        val datum = arrays[position]
        holder.binding.thumb.loadAvatar(Uri.parse(datum.photoUrl))
        holder.binding.txtTitle.text = datum.name
        holder.itemView.setOnClickListener{ onItemSubCategoryProductClick.onItemClick(datum.id!!) }
    }

    override fun getItemCount(): Int  = arrays.size
}