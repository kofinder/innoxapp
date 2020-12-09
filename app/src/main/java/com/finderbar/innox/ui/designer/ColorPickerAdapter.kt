package com.finderbar.innox.ui.designer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemColorPickerCallBack
import com.finderbar.innox.databinding.ItemColorPickerBinding
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class ColorPickerAdapter(
    private val arrays: MutableList<Int>,
    private val itemColorPickerClick: ItemColorPickerCallBack
) : RecyclerView.Adapter<ColorPickerAdapter.ColorPickerViewHolder>() {

    inner class ColorPickerViewHolder(val binding: ItemColorPickerBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemColorPickerBinding.inflate(inflater, parent, false)
        return ColorPickerViewHolder(binding)
    }

    override fun getItemCount(): Int = arrays.size

    override fun onBindViewHolder(holder: ColorPickerViewHolder, position: Int) {
        holder.binding.colorPicker.setBackgroundColor(arrays[position])
        holder.itemView.setOnClickListener {
            itemColorPickerClick.onColorPickerClick(arrays[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position % 2 == 0) 2 else 1
    }
}
