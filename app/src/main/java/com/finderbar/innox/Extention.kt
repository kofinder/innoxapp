package com.finderbar.innox

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.repository.ArtWork
import com.finderbar.innox.repository.CustomLayout
import com.finderbar.innox.repository.Font
import com.finderbar.innox.ui.designer.ButtonGroupAdaptor
import com.google.android.material.button.MaterialButtonToggleGroup


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot : Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int) -> Unit): T {
    itemView.setOnClickListener {event.invoke(adapterPosition) }
    return this
}

interface ItemInStockClick {
    fun onItemClick(categoryId: Int, categoryName: String, subCategoryId: Int, subCategoryName: String)
}

interface ItemProductClick {
    fun onItemClick(_id: Int, position: Int)
}

interface ItemLayoutButtonClick {
    fun onItemClick(_id: Int, arrays: MutableList<CustomLayout>)
}

interface ItemOrderClick {
    fun onItemClick(_id: Int, orderDate: String)
}


interface FragCallBack {
    fun fragListener(frag: Fragment)
    fun backPressed()
}

interface RootFragListener {
    fun onPressed(frag: Fragment)
    fun onBackPressed()
}

interface ItemFontClick {
    fun onItemClick(font: Font)
}

interface ItemArtWorkTitleCallBack {
    fun onItemClick(_id: Int, title: String)
}

interface ItemArtWorkCallBack {
    fun onItemClick(artwork: ArtWork)
}


interface ItemProductCategoryClick {
    fun onItemClick(_id: Int)
}

interface ItemCartCallBack {
    fun onItemClick(carId: Int, quantity: Int)
}

interface ItemColorPickerCallBack {
    fun onColorPickerClick(colorCode: Int)
}

interface ItemFontStyleCallBack  {
    fun onFontStyleClick(isOpen: Boolean, group: MaterialButtonToggleGroup?,
                         checkedId: Int,
                         isChecked: Boolean)
}


fun AppCompatActivity.replaceFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.ft_main, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun RadioButton.setCircleColor(color: Int){
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        ), intArrayOf(
            color,
            color
        )
    )
    buttonTintList = colorStateList
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        buttonTintBlendMode = BlendMode.SRC_IN
    }else{
        buttonTintMode = PorterDuff.Mode.SRC_IN
    }
    invalidate()
}

fun <T> List<T>.copyOf(): List<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}

fun <T> List<T>.mutableCopyOf(): MutableList<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}