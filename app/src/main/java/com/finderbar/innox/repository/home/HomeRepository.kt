package com.finderbar.innox.repository.home

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET


interface HomeRepository {
    @GET("home")
    suspend fun getScreen(): Response<ServiceResponse<HomeScreen>>
}

@Keep
data class ServiceResponse<T>(
    @SerializedName("data") val data: T
)

data class HomeScreen(
    @SerializedName("banners")
    val banners: MutableList<Banner>? = mutableListOf(),
    @SerializedName("categorys")
    val categories: MutableList<Category>? = mutableListOf(),
    @SerializedName("popular_products")
    val popularProducts: MutableList<PopularProduct>? = mutableListOf(),
    @SerializedName("promotion_products")
    val promotionProducts: MutableList<PromotionProduct>? = mutableListOf()
)

@Keep
data class Banner(
    @SerializedName("banner_id") val id: Int? = 0,
    @SerializedName("banner_name") val name: String? = "",
    @SerializedName("image_path") val photoUrl: String? = ""
)

@Keep
data class Category(
    @SerializedName("category_id") val id: Int? = 0,
    @SerializedName("sequence_no") val seqId: Int? = 0,
    @SerializedName("category_name") val name: String? = "",
    @SerializedName("image_path") val photoUrl: String? = ""
)

@Keep
data class PopularProduct(
    @SerializedName("prodcut_id") val id: Int? = 0,
    @SerializedName("product_name") val name: String? = "",
    @SerializedName("price_text") val price: String? = "",
    @SerializedName("images") val images: List<String>? = listOf()
)

@Keep
data class PromotionProduct(
    @SerializedName("prodcut_id") val id: Int? = 0,
    @SerializedName("product_name") val name: String? = "",
    @SerializedName("price_text") val price: String? = "",
    @SerializedName("origninal_price_text") val originalPrice: String? = "",
    @SerializedName("discount_percentage_text") val discount: String? = "",
    @SerializedName("images") val images: List<String>? = listOf()
)