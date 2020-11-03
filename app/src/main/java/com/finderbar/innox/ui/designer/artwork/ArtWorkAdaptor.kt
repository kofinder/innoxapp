package com.finderbar.innox.ui.designer.artwork

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemArtWorkCallBack
import com.finderbar.innox.databinding.ItemArtworkBinding
import com.finderbar.innox.repository.ArtWork
import com.finderbar.innox.utilities.loadLarge

class ArtWorkAdaptor(private val arrays: MutableList<ArtWork>, private val onItemArtWork : ItemArtWorkCallBack) : RecyclerView.Adapter<ArtWorkAdaptor.ArtWorkViewHolder>() {

    inner class ArtWorkViewHolder(val binding: ItemArtworkBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtWorkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtworkBinding.inflate(inflater, parent, false)
        return ArtWorkViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ArtWorkViewHolder, position: Int) {
        val datum = arrays[position]
        holder.binding.thumb.loadLarge(Uri.parse(datum.imageAvatar))
        holder.binding.txtPrice.text = datum.priceText
        holder.itemView.setOnClickListener {
            onItemArtWork.onItemClick(datum)
        }
    }

}