package com.finderbar.innox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot : Boolean = false) : View {
    return LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)
}

fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int) -> Unit): T {
    itemView.setOnClickListener {event.invoke(adapterPosition) }
    return this
}

fun <T> Call<T>.enqueue(success: (response: Response<T>) -> Unit,
                        failure: (t: Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>) = success(response)

        override fun onFailure(call: Call<T>?, t: Throwable) = failure(t)
    })
}

interface ItemProductClick {
    fun onItemClick(_id: Int, position: Int)
}