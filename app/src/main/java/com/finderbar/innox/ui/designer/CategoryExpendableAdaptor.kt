package com.finderbar.innox.ui.designer

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.databinding.ItemExpendableCategoryBinding
import com.finderbar.innox.repository.Category
import com.finderbar.innox.ui.instock.InstockSubCategoryAdaptor
import com.finderbar.innox.utilities.ExpandableLayout
import com.finderbar.innox.utilities.SpaceItemDecoration
import com.finderbar.jovian.utilities.android.loadLarge
import de.hdodenhof.circleimageview.CircleImageView

class CategoryExpendableAdaptor(private val context: Context, private val arrayList: MutableList<Category>) : RecyclerView.Adapter<CategoryExpendableAdaptor.CategoryExpendableViewHolder>() {
    private val expandedPositionSet: HashSet<Int> = HashSet()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryExpendableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemExpendableCategoryBinding.inflate(inflater, parent, false)
        return CategoryExpendableViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: CategoryExpendableViewHolder, position: Int)  {
        val datum : Category = arrayList[position]
        holder.txtTitle.text = datum.name
        holder.thumb.loadLarge(Uri.parse(datum.photoUrl))

        holder.recyclerView.adapter = InstockSubCategoryAdaptor(context, datum.subCategory!!)
        holder.recyclerView.addItemDecoration(SpaceItemDecoration(10));
        holder.recyclerView.layoutManager = GridLayoutManager(context, 2);
        holder.recyclerView.setHasFixedSize(true)
        holder.recyclerView.isNestedScrollingEnabled = false
        holder.recyclerView.itemAnimator = DefaultItemAnimator()
        holder.recyclerView.setRecycledViewPool(RecyclerView.RecycledViewPool());

        holder.expendLayout.setOnExpandListener(object :
            ExpandableLayout.OnExpandListener {
            override fun onExpand(expanded: Boolean) {
                if (expandedPositionSet.contains(position)) {
                    expandedPositionSet.remove(position)
                } else {
                    expandedPositionSet.add(position)
                }
            }
        })
        holder.expendLayout.setExpand(expandedPositionSet.contains(position))
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

    inner class CategoryExpendableViewHolder(val binding: ItemExpendableCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtTitle: TextView = binding.txtTitle
        val thumb: CircleImageView = binding.thumb
        val expendLayout: ExpandableLayout = binding.expandLayout
        val recyclerView: RecyclerView = binding.recyclerView
    }

}