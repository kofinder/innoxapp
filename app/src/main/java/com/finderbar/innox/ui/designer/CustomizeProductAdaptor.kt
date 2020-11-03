package com.finderbar.innox.ui.designer

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.AppConstant.ITEM
import com.finderbar.innox.AppConstant.LOADING
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemCustomizeProductBinding
import com.finderbar.innox.databinding.ItemLoadingBinding
import com.finderbar.innox.repository.Product
import com.finderbar.innox.utilities.loadLarge
import kotlinx.android.synthetic.main.item_popular_product.view.*

class CustomizeProductAdaptor(private val arrayList: MutableList<Product>, private val itemClick : ItemProductClick) : RecyclerView.Adapter<CustomizeProductAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)
    private class CustomizeProductViewHolder(binding: ItemCustomizeProductBinding) : ItemViewHolder(binding.root);
    private class LoadingViewHolder(val binding: ItemLoadingBinding) : ItemViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCustomizeProductBinding.inflate(inflater, parent, false)
        return CustomizeProductViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val datum : Product = arrayList[position]
        val view = holder as CustomizeProductViewHolder
        view.itemView.thumb.loadLarge(Uri.parse(datum.images?.get(0)))
        view.itemView.txt_title.text = datum.name
        view.itemView.txt_price.text = datum.price
        view.itemView.setOnClickListener{ itemClick.onItemClick(datum.id!!, position) }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != 0 && !(position == itemCount - 1)) {
            ITEM
        } else {
            LOADING
        }
    }

    private fun add(r: Product) {
        arrayList.add(r)
        notifyItemInserted(arrayList.size - 1)
    }

    fun addAll(products: List<Product>) {
        for (result in products) {
            add(result)
        }
    }

}