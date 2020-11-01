package com.finderbar.innox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.finderbar.innox.repository.Font


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

interface ItemOrderClick {
    fun onItemClick(_id: Int, orderDate: String)
}

interface ItemArtWorkCallBack {
    fun onItemClick(_id: Int, title: String)
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

interface ItemProductCategoryClick {
    fun onItemClick(_id: Int)
}

interface ItemCartCallBack {
    fun onItemClick(carId: Int, quantity: Int)
}


fun AppCompatActivity.replaceFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.ft_main, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
}