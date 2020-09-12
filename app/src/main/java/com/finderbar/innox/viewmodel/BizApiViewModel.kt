package com.finderbar.innox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.finderbar.innox.network.ApiClientHandler
import com.finderbar.innox.repository.BizApiRepository
import com.finderbar.innox.network.Resource
import kotlinx.coroutines.Dispatchers

class BizApiViewModel : ViewModel() {

    fun loadData() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getScreen()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadCategories()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getCategory()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadProduct(productId: String)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getProduct(productId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadSearchProduct(keyword: String, startPrice: String, endPrice: String, categoryId: String, subCategoryId: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.searchProduct(keyword, startPrice, endPrice, categoryId, subCategoryId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadCustomSubCategoryProduct(subCategoryId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getAllCustomSubCategoryProduct(subCategoryId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadDesignerProduct(productId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getDesignerProduct(productId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadArtWorkDesigner()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getArtWorkDesigner()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadArtWorkByDesignerId(designerId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getArtWorkByDesignerId(designerId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadArtWorkCategory()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getArtWorkCategory()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }


    fun loadArtWorkByCategoryId(categoryId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getArtWorkByCategoryId(categoryId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }


}