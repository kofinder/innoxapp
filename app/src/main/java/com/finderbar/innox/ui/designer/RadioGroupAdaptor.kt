package com.finderbar.innox.ui.designer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemCustomizeDesingerRadioGroupBinding
import com.finderbar.innox.repository.CustomLayout

class RadioGroupAdaptor(private val arrays: MutableList<CustomLayout>, private val itemClick : ItemProductClick) : RecyclerView.Adapter<RadioGroupAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)

    private class DesignerRadioGroupViewHolder(binding: ItemCustomizeDesingerRadioGroupBinding) : ItemViewHolder(binding.root) {
        val rdoColor = binding.rdoColor
    }

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCustomizeDesingerRadioGroupBinding.inflate(inflater, parent, false)
        return DesignerRadioGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val view = holder as DesignerRadioGroupViewHolder
        val datum = arrays[position]
        //view.btnAction.text = datum.name
    }
}