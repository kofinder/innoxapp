package com.finderbar.innox.ui.designer.fontstyle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemFontClick
import com.finderbar.innox.databinding.ItemFontBinding
import com.finderbar.innox.repository.Font

class FontAdaptor(private val arrays: MutableList<Font>, private val itemClick : ItemFontClick) : RecyclerView.Adapter<FontAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)

    private class FontViewHolder(binding: ItemFontBinding) : ItemViewHolder(binding.root) {
        val txtName = binding.txtName
    }

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFontBinding.inflate(inflater, parent, false)
        return FontViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val view = holder as FontViewHolder
        val datum = arrays[position]
        view.txtName.text = datum.name
        view.itemView.setOnClickListener{
            itemClick.onItemClick(datum)
        }
    }

}