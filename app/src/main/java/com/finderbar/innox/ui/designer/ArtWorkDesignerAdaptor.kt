package com.finderbar.innox.ui.designer

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemArtworkDesignerBinding
import com.finderbar.innox.repository.ArtWork
import com.finderbar.jovian.utilities.android.loadLarge


class ArtWorkDesignerAdaptor(private val arrays: MutableList<ArtWork>, private val itemClick : ItemProductClick) : RecyclerView.Adapter<ArtWorkDesignerAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)

    private class ArtworkDesignerViewHolder(binding: ItemArtworkDesignerBinding) : ItemViewHolder(binding.root) {
        val imageAvatar = binding.civThumb
        val txtName = binding.txtName
    }

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtworkDesignerBinding.inflate(inflater, parent, false)
        val view = ArtworkDesignerViewHolder(binding)
        val height = parent.measuredHeight / 4
        view.itemView.minimumHeight = height
        return view
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val view = holder as ArtworkDesignerViewHolder
        val datum = arrays[position]
        view.imageAvatar.loadLarge(Uri.parse(datum.imageAvatar))
        view. txtName.text = datum.name
    }
}