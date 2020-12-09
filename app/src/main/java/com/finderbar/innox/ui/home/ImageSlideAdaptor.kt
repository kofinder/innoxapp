package com.finderbar.innox.ui.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.finderbar.innox.databinding.ItemImageSliderBinding
import com.finderbar.innox.repository.Banner
import com.finderbar.innox.utilities.loadLarge
import com.smarteist.autoimageslider.SliderViewAdapter

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class ImageSlideAdaptor(private val arrayList: MutableList<Banner>): SliderViewAdapter<ImageSlideAdaptor.SliderViewHolder>() {

    inner class SliderViewHolder(binding: ItemImageSliderBinding) : ViewHolder(binding.root) {
        var txtTitle: TextView? = binding.txtTitle
        var imgSlide: ImageView = binding.imgSlider
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = ItemImageSliderBinding.inflate(inflater, parent, false)
        return SliderViewHolder(binding)
    }

    override fun getCount(): Int = arrayList.size

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        val banner = arrayList[position]
        viewHolder?.txtTitle?.text = banner.name
        viewHolder?.imgSlide?.loadLarge(Uri.parse(banner.photoUrl))
    }


    private fun add(r: Banner) {
        arrayList.add(r)
        notifyDataSetChanged()
    }

    fun addAll(resultList: List<Banner>) {
        for (result in resultList) {
            add(result)
        }
    }

}
