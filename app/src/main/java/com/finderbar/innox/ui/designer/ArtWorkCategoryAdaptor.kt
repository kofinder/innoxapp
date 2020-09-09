package com.finderbar.innox.ui.designer

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemArtworkCategoryBinding
import com.finderbar.innox.databinding.ItemArtworkDesignerBinding
import com.finderbar.innox.repository.ArtWork
import com.finderbar.innox.repository.ArtWorkCategory
import com.finderbar.jovian.utilities.android.loadLarge

class ArtWorkCategoryAdaptor(private val arrays: MutableList<ArtWorkCategory>, private val itemClick : ItemProductClick) : RecyclerView.Adapter<ArtWorkCategoryAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)

    private class ArtWorkCategoryViewHolder(binding: ItemArtworkCategoryBinding) : ItemViewHolder(binding.root) {
        val btnArtwork = binding.btnArtwork
    }

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtworkCategoryBinding.inflate(inflater, parent, false)
        return ArtWorkCategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val view = holder as ArtWorkCategoryViewHolder
        val datum = arrays[position]
        view.btnArtwork.text = datum.name
    }
}