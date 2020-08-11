package com.finderbar.innox.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.AppConstant.ITEM
import com.finderbar.innox.AppConstant.LOADING
import com.finderbar.innox.databinding.ItemCategoryProductBinding
import com.finderbar.innox.databinding.ItemLoadingBinding
import com.finderbar.innox.repository.home.Category
import com.finderbar.jovian.utilities.android.loadAvatar
import kotlinx.android.synthetic.main.item_category_product.view.*

class CategoryProductAdaptor(private val arrayList: MutableList<Category>) : RecyclerView.Adapter<CategoryProductAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)
    private class CategoryProductViewHolder(binding: ItemCategoryProductBinding) : ItemViewHolder(binding.root);
    private class LoadingViewHolder(val binding: ItemLoadingBinding) : ItemViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryProductBinding.inflate(inflater, parent, false)
        return CategoryProductViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val datum : Category = arrayList[position]
        val view = holder as CategoryProductViewHolder
        view.itemView.civ_thumb?.loadAvatar(Uri.parse(datum.photoUrl))
        view.itemView.txt_name.text = datum.name
    }

    override fun getItemViewType(position: Int): Int {
        return if (position != 0 && !(position == itemCount - 1)) {
            ITEM
        } else {
            LOADING
        }
    }

    private fun add(r: Category) {
        arrayList.add(r)
        notifyItemInserted(arrayList.size - 1)
    }

    fun addAll(cates: List<Category>) {
        for (result in cates) {
            add(result)
        }
    }

}