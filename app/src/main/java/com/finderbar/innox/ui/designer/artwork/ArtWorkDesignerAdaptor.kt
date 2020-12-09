package com.finderbar.innox.ui.designer.artwork

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.ItemArtWorkTitleCallBack
import com.finderbar.innox.databinding.ItemArtworkDesignerBinding
import com.finderbar.innox.repository.ArtWorkDesigner
import com.finderbar.innox.utilities.loadLarge
/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class ArtWorkDesignerAdaptor(
    private val arrays: MutableList<ArtWorkDesigner>,
    private val itemClick: ItemArtWorkTitleCallBack
) : RecyclerView.Adapter<ArtWorkDesignerAdaptor.ArtworkDesignerViewHolder>() {

    class ArtworkDesignerViewHolder(val binding: ItemArtworkDesignerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = arrays.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtworkDesignerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArtworkDesignerBinding.inflate(inflater, parent, false)
        return ArtworkDesignerViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: ArtworkDesignerViewHolder, position: Int) {
        val datum = arrays[position]
        holder.binding.civThumb.loadLarge(Uri.parse(datum.imageAvatar))
        holder.binding.txtName.text = datum.name
        holder.itemView.setOnClickListener{
            itemClick.onItemClick(datum.id, datum.name)
        }
    }

}