package com.finderbar.innox.ui.designer

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductCategoryClick
import com.finderbar.innox.databinding.ItemDesignerProductBinding
import com.finderbar.innox.repository.Category
import com.finderbar.jovian.utilities.android.loadLarge

class DesignerAdaptor(private val arrayList: MutableList<Category>, private val itemProductClick: ItemProductCategoryClick) : RecyclerView.Adapter<DesignerAdaptor.DesignerProductViewHolder>() {

    inner class DesignerProductViewHolder(val binding: ItemDesignerProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignerProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDesignerProductBinding.inflate(inflater, parent, false)
        return DesignerProductViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: DesignerProductViewHolder, position: Int) {
        val datum : Category = arrayList[position]
        holder.binding.thumb.loadLarge(Uri.parse(datum.photoUrl));
        holder.binding.btnCreate.text = datum.name
        holder.itemView.setOnClickListener{
            itemProductClick.onItemClick(datum.id!!)
        }
    }

    override fun getItemViewType(position: Int): Int {
       return if(position % 2 == 0) 2 else 1
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
