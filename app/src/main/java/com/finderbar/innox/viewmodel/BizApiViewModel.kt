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
        try {
            val response = api.getScreen()
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadCategories()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getCategory()
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadProduct(productId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getProduct(productId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadSearchProduct(keyword: String, startPrice: Int, endPrice: Int, categoryId: Int, subCategoryId: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.searchProduct(keyword, startPrice, endPrice, categoryId, subCategoryId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadCustomSubCategoryProduct(subCategoryId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getAllCustomSubCategoryProduct(subCategoryId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadDesignerProduct(productId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getDesignerProduct(productId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadArtWorkDesigner()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getArtWorkDesigner()
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadArtWorkByDesignerId(designerId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getArtWorkByDesignerId(designerId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadArtWorkCategory()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getArtWorkCategory()
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }


    fun loadArtWorkByCategoryId(categoryId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getArtWorkByCategoryId(categoryId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }


    fun loadFont()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getFonts()
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadState()  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getStates()
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }


    fun loadTownship(stateId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getTownships(stateId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadTokenByRegister(register: Register)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.register(register)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadTokenByLogin(login: Login)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = ApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.login(login)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }


    fun loadAddToCart(cart: ShoppingCart)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.saveShoppingCart(cart)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadShoppingCart() = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getShoppingCart()
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }


    fun loadEditShoppingCart(cartId: Int, quantity: Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.editShoppingCart(cartId, quantity)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadDeleteShoppingCart(cart: CartIds) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.deleteShoppingCart(cart)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadOrderPreload(cart: CartIds) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.orderPreload(cart)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadConfirmOrder(order: ConfirmOrder) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.confirmOrder(order)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }


    fun loadUserProfile(userId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getUserProfile(userId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }


    fun loadOrderHistory(status: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getOrderHistory(status)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }
    }

    fun loadOrderHistoryId(orderId: Int)  = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val api = AuthApiClientHandler.createService(BizApiRepository::class.java)
            val response = api.getOrderHistoryById(orderId)
            emit(Resource.success(response.body()?.data))
        } catch (ex: Exception) {
            emit(Resource.error(500, ex.message!!))
        }


    }

}