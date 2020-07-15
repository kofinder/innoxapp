package com.finderbar.innox.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.finderbar.innox.R
import com.smarteist.autoimageslider.SliderViewAdapter


class ImageSlideAdaptor(private val context: Context): SliderViewAdapter<ImageSlideAdaptor.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent?.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun getCount(): Int = 4

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        when (position) {
            0 -> Glide.with(viewHolder?.itemView!!)
                .load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                .into(viewHolder.imageViewBackground)
            1 -> Glide.with(viewHolder!!.itemView)
                .load("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260")
                .into(viewHolder.imageViewBackground)
            2 -> Glide.with(viewHolder!!.itemView)
                .load("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                .into(viewHolder.imageViewBackground)
            else -> Glide.with(viewHolder!!.itemView)
                .load("https://images.pexels.com/photos/218983/pexels-photo-218983.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260")
                .into(viewHolder.imageViewBackground)
        }
    }

    inner class SliderAdapterVH(itemView: View): ViewHolder(itemView) {
        var itemView: View = itemView
        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
        var textViewDescription: TextView = itemView.findViewById(R.id.tv_auto_image_slider)
    }
}
