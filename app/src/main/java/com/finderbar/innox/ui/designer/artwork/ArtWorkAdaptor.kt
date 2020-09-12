package com.finderbar.innox.ui.designer.artwork

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemArtworkBinding
import com.finderbar.innox.repository.ArtWork
import com.finderbar.jovian.utilities.android.loadLarge

class ArtWorkAdaptor(private val arrays: MutableList<ArtWork>, private val itemClick : ItemProductClick) : RecyclerView.Adapter<ArtWorkAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)

    private class ArtWorkViewHolder(binding: ItemArtworkBinding) : ItemViewHolder(binding.root) {
        val imgAvatar = binding.thumb
        val txtPrice = binding.txtPrice
    }

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtworkBinding.inflate(inflater, parent, false)
        return ArtWorkViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val view = holder as ArtWorkViewHolder
        val datum = arrays[position]
        view.imgAvatar.loadLarge(Uri.parse(datum.imageAvatar))
        view.txtPrice.text = datum.priceText
    }

}