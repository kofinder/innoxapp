package com.finderbar.innox.ui.instock

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.R
import com.finderbar.innox.inflate
import com.finderbar.innox.repository.home.Product
import com.finderbar.jovian.utilities.android.loadLarge

class InstockProductAdaptor(private val arrayList: MutableList<Product>) : RecyclerView.Adapter<InstockProductAdaptor.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder = ProductViewHolder(parent.inflate(R.layout.item_popular_product))

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bindData(arrayList[position])

    private fun add(r: Product) {
        arrayList.add(r)
        notifyItemInserted(arrayList.size - 1)
    }

    fun addAll(resultList: List<Product>) {
        for (result in resultList) {
            add(result)
        }
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumb: ImageView = itemView.findViewById(R.id.thumb)
        val title: TextView = itemView.findViewById(R.id.txt_title)
        fun bindData(product: Product) {
            thumb.loadLarge(Uri.parse(product.url));
            title.text = product.description
        }
    }
}