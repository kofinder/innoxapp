package com.finderbar.innox.ui.designer.fontstyle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemFontClick
import com.finderbar.innox.databinding.ItemFontBinding
import com.finderbar.innox.repository.Font

class FontAdaptor(private val arrays: MutableList<Font>, val itemClick : ItemFontClick) : RecyclerView.Adapter<FontAdaptor.FontViewHolder>() {

    class FontViewHolder(val binding: ItemFontBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FontViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFontBinding.inflate(inflater, parent, false)
        return FontViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FontViewHolder, position: Int) {
        val datum = arrays[position]
        holder.binding.txtName.text = datum.name
        holder.itemView.setOnClickListener{
            itemClick.onItemClick(datum)
        }
    }

}