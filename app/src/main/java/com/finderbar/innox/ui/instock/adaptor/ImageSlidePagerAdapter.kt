package com.finderbar.innox.ui.instock.adaptor

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.finderbar.innox.R
import com.finderbar.innox.utilities.loadLarge


class ImageSlidePagerAdapter(val context: Context, private val images: MutableList<String>): PagerAdapter() {

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout: View = LayoutInflater.from(context).inflate(R.layout.item_image_detail_slider, view, false)
        val img: ImageView = imageLayout.findViewById(R.id.image)
        img.loadLarge(Uri.parse(images[position]))
        view.addView(imageLayout, 0);
        return imageLayout
    }

    override fun getCount(): Int = images.size

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) = container.removeView(obj as View)

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj as View
}
