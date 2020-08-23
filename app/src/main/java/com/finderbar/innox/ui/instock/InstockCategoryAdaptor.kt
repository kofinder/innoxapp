package com.finderbar.innox.ui.instock

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.databinding.ItemInstockCategoryBinding
import com.finderbar.innox.repository.Category

class InstockCategoryAdaptor(private val context: Context, private val arrayList: MutableList<Category>) : RecyclerView.Adapter<InstockCategoryAdaptor.CategoryInstockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryInstockViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstockCategoryBinding.inflate(inflater, parent, false)
        return CategoryInstockViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: CategoryInstockViewHolder, position: Int)  {
        val datum : Category = arrayList[position]
        holder.txtTitle.text = datum.name
        holder.recyclerView.adapter = InstockSubCategoryAdaptor(context, datum.subCategory!!)
        holder.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.recyclerView.setHasFixedSize(true)
        holder.recyclerView.isNestedScrollingEnabled = false
        holder.recyclerView.itemAnimator = DefaultItemAnimator()
        holder.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());
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

    inner class CategoryInstockViewHolder(val binding: ItemInstockCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.recyclerView
        val txtTitle: TextView = binding.txtTitle
    }

}
