package com.finderbar.innox.repository

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CoreApiRepository {
    @GET("home")
    suspend fun getScreen(): Response<ServiceResponse<HomeScreen>>

    @GET("categorys")
    suspend fun getCategory(): Response<ServiceResponse<Categories>>

    @GET("product")
    suspend fun getProduct(@Query("product_id") productId: String): Response<ServiceResponse<ProductDetail>>
}

@Keep
data class ServiceResponse<T>(
    @SerializedName("data") val data: T,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("errorList") val error: ArrayList<Any>
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
    @SerializedName("image_path") val photoUrl: String? = "",
    @SerializedName("sub_categorys") val subCategory: MutableList<SubCategory>? = mutableListOf()
)

@Keep
data class Categories(
    @SerializedName("categorys")
    val categories: MutableList<Category>? = mutableListOf()
)

@Keep
data class SubCategory(
    @SerializedName("sub_category_id") val id: Int? = 0,
    @SerializedName("sequence_no") val seqId: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("image_path") val photoUrl: String? = ""
)

@Keep
data class Product(
    @SerializedName("prodcut_id") val id: Int? = 0,
    @SerializedName("product_name") val name: String? = "",
    @SerializedName("price_text") val price: String? = "",
    @SerializedName("images") val images: List<String>? = listOf()
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

@Keep
data class ProductDetail(
    @SerializedName("product_id") val id: Int? = 0,
    @SerializedName("product_name") val name: String? = "",
    @SerializedName("images") val images: List<String>? = listOf(),
    @SerializedName("price_text") val price: String? = "",
    @SerializedName("origninal_price_text") val originalPrice: String? = "",
    @SerializedName("discount_percentage_text") val discount: String? = "",
    @SerializedName("is_promotion") val isPromotion: Boolean? = false,
    @SerializedName("is_popular") val isPopular: Boolean? = false,
    @SerializedName("is_new_arrival") val isNewArrival: Boolean? = false,
    @SerializedName("detail") val description: String? = "",
    @SerializedName("color_list") val colors: MutableList<Colors>? = mutableListOf(),
    @SerializedName("size_list") val sizes: MutableList<Sizes>? = mutableListOf()
)

@Keep
data class Colors(
    @SerializedName("color_id") val id: Int,
    @SerializedName("color_name") val name: String,
    @SerializedName("color_code") val code: String
)

@Keep
data class Sizes(
    @SerializedName("size_id") val id: Int,
    @SerializedName("size_name") val name: String,
    @SerializedName("size_code") val code: String
)