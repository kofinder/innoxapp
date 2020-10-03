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

class InStockCategoryAdaptor(private val context: Context, private val arrayList: MutableList<Category>) :
    RecyclerView.Adapter<InStockCategoryAdaptor.InStockCategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InStockCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstockCategoryBinding.inflate(inflater, parent, false)
        return InStockCategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: InStockCategoryViewHolder, position: Int)  {
        val datum : Category = arrayList[position]
        holder.txtTitle.text = datum.name
        holder.recyclerView.adapter = InStockSubCategoryAdaptor(datum.subCategory!!, null)
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

    inner class InStockCategoryViewHolder(val binding: ItemInstockCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val recyclerView: RecyclerView = binding.recyclerView
        val txtTitle: TextView = binding.txtTitle
    }

}
