package com.finderbar.innox.ui.designer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemLayoutButtonClick
import com.finderbar.innox.databinding.ItemCustomizeDesignerButtonGroupBinding
import com.finderbar.innox.repository.CustomLayout

class ButtonGroupAdaptor(private val arrays: MutableList<CustomLayout>, private val itemClick : ItemLayoutButtonClick) : RecyclerView.Adapter<ButtonGroupAdaptor.DesignerButtonGroupViewHolder>() {

    class DesignerButtonGroupViewHolder(val binding: ItemCustomizeDesignerButtonGroupBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesignerButtonGroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCustomizeDesignerButtonGroupBinding.inflate(inflater, parent, false)
        return DesignerButtonGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DesignerButtonGroupViewHolder, position: Int) {
        val datum = arrays[position]
        holder.binding.btnAction.text = datum.name
        holder.binding.btnAction.setOnClickListener {
            itemClick.onItemClick(datum.id, arrays)
        }
    }
}