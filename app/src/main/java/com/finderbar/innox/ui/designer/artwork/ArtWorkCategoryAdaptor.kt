package com.finderbar.innox.ui.designer.artwork

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemArtWorkTitleCallBack
import com.finderbar.innox.databinding.ItemArtworkCategoryBinding
import com.finderbar.innox.repository.ArtWorkCategory

class ArtWorkCategoryAdaptor(private val arrays: MutableList<ArtWorkCategory>, private val itemClick : ItemArtWorkTitleCallBack) : RecyclerView.Adapter<ArtWorkCategoryAdaptor.ArtWorkCategoryViewHolder>() {

    class ArtWorkCategoryViewHolder(val binding: ItemArtworkCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorkCategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtworkCategoryBinding.inflate(inflater, parent, false)
        return ArtWorkCategoryViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ArtWorkCategoryViewHolder, position: Int) {
        val datum = arrays[position]
        holder.binding.btnArtwork.text = datum.name
        holder.itemView.setOnClickListener{
            itemClick.onItemClick(datum.id, datum.name)
        }
    }
}