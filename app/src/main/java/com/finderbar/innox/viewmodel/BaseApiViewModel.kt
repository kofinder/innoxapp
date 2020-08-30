package com.finderbar.innox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.finderbar.innox.repository.ApiClient
import com.finderbar.innox.repository.CoreApiRepository
import com.finderbar.innox.repository.Resource
import kotlinx.coroutines.Dispatchers

class BaseApiViewModel : ViewModel() {

    fun loadData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClient.createService(CoreApiRepository::class.java)
        val response = api.getScreen()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadCategories()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClient.createService(CoreApiRepository::class.java)
        val response = api.getCategory()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadProduct(productId: String)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClient.createService(CoreApiRepository::class.java)
        val response = api.getProduct(productId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadSearchProduct(keyword: String, startPrice: String, endPrice: String, categoryId: String, subCategoryId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClient.createService(CoreApiRepository::class.java)
        val response = api.searchProduct(keyword, startPrice, endPrice, categoryId, subCategoryId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

}