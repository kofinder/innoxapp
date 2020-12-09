package com.finderbar.innox.ui.instock

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemInStockClick
import com.finderbar.innox.databinding.ItemInstockSubCategoryBinding
import com.finderbar.innox.repository.SubCategory
import com.finderbar.innox.utilities.loadLarge
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class InStockSubCategoryAdaptor(val categoryId: Int, private val categoryName: String, val arrays: MutableList<SubCategory>, private val onItemInStockClick: ItemInStockClick) :
    RecyclerView.Adapter<InStockSubCategoryAdaptor.SubCategoryViewHolder>() {

    class SubCategoryViewHolder(val binding: ItemInstockSubCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstockSubCategoryBinding.inflate(inflater, parent, false)
        return SubCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = arrays.size

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int)  {
        val datum : SubCategory = arrays[position]
        holder.itemView.setOnClickListener{ onItemInStockClick.onItemClick(categoryId, categoryName, datum.id!!, datum.name!!) }
        holder.binding.txtTitle.text = datum.name
        holder.binding.thumb.loadLarge(Uri.parse(datum.photoUrl))
    }
}
