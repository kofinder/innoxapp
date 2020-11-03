package com.finderbar.innox.ui.instock.adaptor

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemInstockSubProductBinding
import com.finderbar.innox.repository.Product
import com.finderbar.innox.utilities.loadLarge

class InStockProductAdaptor(
    private val arrays: MutableList<Product>,
    private val onItemProductClick: ItemProductClick
): RecyclerView.Adapter<InStockProductAdaptor.InStockSubProductViewHolder>() {

    class InStockSubProductViewHolder(val binding: ItemInstockSubProductBinding): RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InStockSubProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstockSubProductBinding.inflate(inflater, parent, false)
        return InStockSubProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InStockSubProductViewHolder, position: Int) {
        val datum = arrays[position]
        holder.binding.thumb.loadLarge(Uri.parse(datum.images?.get(0)!!))
        holder.binding.txtTitle.text = datum.name
        holder.itemView.setOnClickListener{ onItemProductClick.onItemClick(datum.id!!, position) }
    }

    override fun getItemCount(): Int  = arrays.size

    private fun add(r: Product) {
        arrays.add(r)
        notifyItemInserted(arrays.size - 1)
    }

    fun addAll(arrays: List<Product>) {
        for (arr in arrays) {
            add(arr)
        }
    }

    fun clear() {
        val size: Int = arrays.size
        arrays.clear()
        notifyItemRangeRemoved(0, size)
    }
}