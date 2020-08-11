package com.finderbar.innox.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.AppConstant.ITEM
import com.finderbar.innox.AppConstant.LOADING
import com.finderbar.innox.databinding.ItemLoadingBinding
import com.finderbar.innox.databinding.ItemPopularProductBinding
import com.finderbar.innox.repository.home.PopularProduct
import com.finderbar.jovian.utilities.android.loadLarge
import kotlinx.android.synthetic.main.item_popular_product.view.*

class PopularProductAdaptor(private val arrayList: MutableList<PopularProduct>) : RecyclerView.Adapter<PopularProductAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)
    private class PopularProductViewHolder(binding: ItemPopularProductBinding) : ItemViewHolder(binding.root);
    private class LoadingViewHolder(val binding: ItemLoadingBinding) : ItemViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPopularProductBinding.inflate(inflater, parent, false)
        return PopularProductViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val datum : PopularProduct = arrayList[position]
        val view = holder as PopularProductViewHolder
        holder.itemView.thumb.loadLarge(Uri.parse(datum.images?.get(0)))
        holder.itemView.txt_title.text = datum.name
        holder.itemView.txt_price.text = datum.price
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != 0 && !(position == itemCount - 1)) {
            ITEM
        } else {
            LOADING
        }
    }

    private fun add(r: PopularProduct) {
        arrayList.add(r)
        notifyItemInserted(arrayList.size - 1)
    }

    fun addAll(products: List<PopularProduct>) {
        for (result in products) {
            add(result)
        }
    }

}