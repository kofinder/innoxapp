package com.finderbar.innox.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.finderbar.innox.network.ApiClientHandler
import com.finderbar.innox.network.AuthApiClientHandler
import com.finderbar.innox.network.Resource
import com.finderbar.innox.repository.*
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

    fun loadProduct(productId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getProduct(productId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadSearchProduct(keyword: String, startPrice: Int, endPrice: Int, categoryId: Int, subCategoryId: Int) = liveData(Dispatchers.IO) {
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


    fun loadFont()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getFonts()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadState()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getStates()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }


    fun loadTownship(stateId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getTownships(stateId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadTokenByRegister(register: Register)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.register(register)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadTokenByLogin(login: Login)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = ApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.login(login)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }


    fun loadAddToCart(cart: ShoppingCart)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.saveShoppingCart(cart)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadShoppingCart() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getShoppingCart()
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }


    fun loadEditShoppingCart(cartId: Int, quantity: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.editShoppingCart(cartId, quantity)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadDeleteShoppingCart(cart: CartIds) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.deleteShoppingCart(cart)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadOrderPreload(cart: CartIds) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.orderPreload(cart)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadConfirmOrder(order: ConfirmOrder) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.confirmOrder(order)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }


    fun loadUserProfile(userId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getUserProfile(userId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }


    fun loadOrderHistory(status: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getOrderHistory(status)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

    fun loadOrderHistoryId(orderId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
        val response = api.getOrderHistoryById(orderId)
        if(response.isSuccessful) {
            emit(Resource.success(response.body()?.data))
        }
    }

}