package com.finderbar.innox.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.AppConstant.ITEM
import com.finderbar.innox.AppConstant.LOADING
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemLoadingBinding
import com.finderbar.innox.databinding.ItemPromotionProductBinding
import com.finderbar.innox.repository.PromotionProduct
import com.finderbar.jovian.utilities.android.loadLarge
import kotlinx.android.synthetic.main.item_popular_product.view.*

class PromotionProductAdaptor(private val arrayList: MutableList<PromotionProduct>, private val itemClick : ItemProductClick) : RecyclerView.Adapter<PromotionProductAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)
    private class PromotionProductViewHolder(binding: ItemPromotionProductBinding) : ItemViewHolder(binding.root);
    private class LoadingViewHolder(val binding: ItemLoadingBinding) : ItemViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPromotionProductBinding.inflate(inflater, parent, false)
        return PromotionProductViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val datum : PromotionProduct = arrayList[position]
        val view = holder as PromotionProductViewHolder
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

    private fun add(r: PromotionProduct) {
        arrayList.add(r)
        notifyItemInserted(arrayList.size - 1)
    }

    fun addAll(products: List<PromotionProduct>) {
        for (result in products) {
            add(result)
        }
    }

}