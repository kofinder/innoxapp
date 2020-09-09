package com.finderbar.innox.ui.designer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemProductClick
import com.finderbar.innox.databinding.ItemCustomizeDesignerButtonGroupBinding
import com.finderbar.innox.repository.CustomLayout

class ButtonGroupAdaptor(private val arrays: MutableList<CustomLayout>, private val itemClick : ItemProductClick) : RecyclerView.Adapter<ButtonGroupAdaptor.ItemViewHolder>() {

    abstract class ItemViewHolder(root: View) : RecyclerView.ViewHolder(root)

    private class DesignerButtonGroupViewHolder(binding: ItemCustomizeDesignerButtonGroupBinding) : ItemViewHolder(binding.root) {
        val btnAction = binding.btnAction
    }

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCustomizeDesignerButtonGroupBinding.inflate(inflater, parent, false)
        return DesignerButtonGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
       val view = holder as DesignerButtonGroupViewHolder
        val datum = arrays[position]
        view.btnAction.text = datum.name
    }
}