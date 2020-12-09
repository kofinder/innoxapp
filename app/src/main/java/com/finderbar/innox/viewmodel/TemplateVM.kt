package com.finderbar.innox.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finderbar.innox.repository.CustomItems
import com.finderbar.innox.repository.CustomLayout

/**
 * Created by: finderbar
 * Created at: 09,December,2020
 */
class TemplateVM: ViewModel() {

     var items: MutableLiveData<CustomItems>? = MutableLiveData()
     var layouts: MutableLiveData<CustomLayout>? = MutableLiveData()

    fun getTemplate(colorId: Int?, arrays: MutableList<CustomItems>) {
        if(colorId!! > 0 ) {
            arrays.forEach { x ->
                if(x.colorId == colorId) {
                    items?.postValue(x)
                }
            }
        } else {
            items?.postValue(arrays.first())
        }
    }

    fun getLayout(id: Int?, arrays: MutableList<CustomLayout>) {
        if(id!! > 0) {
            arrays.forEach { x ->
                if(x.id == id) {
                    layouts?.postValue(x)
                }
            }
        } else {
            layouts?.postValue(arrays.first())
        }
    }

}