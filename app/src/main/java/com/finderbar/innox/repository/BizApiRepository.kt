package com.finderbar.innox.repository

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BizApiRepository {
    @GET("home")
    suspend fun getScreen(): Response<ServiceResponse<HomeScreen>>

    @GET("categorys")
    suspend fun getCategory(): Response<ServiceResponse<Categories>>

    @GET("product")
    suspend fun getProduct(@Query("product_id") productId: String): Response<ServiceResponse<ProductDetail>>

    @GET("products/search")
    suspend fun searchProduct(@Query("keyword") keyword: String,
                              @Query("startPrice") startPrice: String,
                              @Query("endPrice") endPrice: String,
                              @Query("category_id") categoryId: String,
                              @Query("sub_category_id") subCategoryId: String): Response<ServiceResponse<Products>>

    @GET("custom_products/list_by_sub_category")
    suspend fun getAllCustomSubCategoryProduct(@Query("sub_category_id") subCategoryId: Int): Response<ServiceResponse<CustomProducts>>

    @GET("custom_products/detail")
    suspend fun getDesignerProduct(@Query("custom_product_id") productId: Int): Response<ServiceResponse<Designer>>

    @GET("artwork/categorys")
    suspend fun getArtWorkCategory(): Response<ServiceResponse<ArtWorkCategoreis>>

    @GET("artworks_by_category")
    suspend fun getArtWorkByCategoryId(@Query("artwork_category_id") categoryId: Int): Response<ServiceResponse<ArtWorks>>

    @GET("artwork/designers")
    suspend fun getArtWorkDesigner(): Response<ServiceResponse<ArtWorkDesigners>>

    @GET("artworks_by_designer")
    suspend fun getArtWorkByDesignerId(@Query("designer_id") designerId: Int): Response<ServiceResponse<ArtWorks>>

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
data class Categories(
    @SerializedName("categorys")
    val categories: MutableList<Category>? = mutableListOf()
)

@Keep
data class Products(
    @SerializedName("products")
    val products: MutableList<PromotionProduct>? = mutableListOf()
)

@Keep
data class CustomProducts(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("has_more_list")
    val hasNex: Boolean = false,
    @SerializedName("products")
    val products: MutableList<Product>? = mutableListOf()
)

@Keep
data class ArtWorkCategoreis(
    @SerializedName("artwork_categorys")
    val artWorkCategory: MutableList<ArtWorkCategory>? = mutableListOf()
)

@Keep
data class ArtWorkDesigners(
    @SerializedName("artwork_designers")
    val artWorkDesigner: MutableList<ArtWorkDesigner>? = mutableListOf()
)

data class ArtWorks(
    @SerializedName("artworks")
    val artWork: MutableList<ArtWork>? = mutableListOf()
)


// DOMAIN MODEL
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

@Keep
data class Designer(
    @SerializedName("custom_product_id") val id: Int,
    @SerializedName("product_name") val name: String,
    @SerializedName("initial_price") val price: Int,
    @SerializedName("initial_price_text") val priceText: String,
    @SerializedName("custom_items") val customItems:  MutableList<CustomItems>? = mutableListOf()
)

@Keep
data class CustomItems(
    @SerializedName("custom_item_id") val id: Int,
    @SerializedName("sequence_no") val seq: Int,
    @SerializedName("item_name") val name: String,
    @SerializedName("item_price") val price: Int,
    @SerializedName("item_price_text") val priceText: String,
    @SerializedName("color_id") val colorId: Int,
    @SerializedName("color_code") val colorCode: String,
    @SerializedName("color_name") val colorName: String,
    @SerializedName("custom_item_layouts") val customLayout:  MutableList<CustomLayout>? = mutableListOf()
)

@Keep
data class CustomLayout(
    @SerializedName("custom_item_layout_id") val id: Int,
    @SerializedName("sequence_no") val seq: Int,
    @SerializedName("layout_name") val name: String,
    @SerializedName("layout_price") val price: Int,
    @SerializedName("layout_price_text") val priceText: String,
    @SerializedName("layout_image") val imageAvatar: String
)

@Keep
data class ArtWork(
    @SerializedName("artwork_id") val id: Int,
    @SerializedName("artwork_name") val name: String,
    @SerializedName("artwork_price") val price: String,
    @SerializedName("artwork_price_text") val priceText: String,
    @SerializedName("artwork_image") val imageAvatar: String
)

@Keep
data class ArtWorkCategory(
    @SerializedName("category_id") val id: Int,
    @SerializedName("category_name") val name: String,
    @SerializedName("category_code") val code: String
)

@Keep
data class ArtWorkDesigner(
    @SerializedName("designer_id") val id: Int,
    @SerializedName("designer_name") val name: String,
    @SerializedName("designer_avatar") val imageAvatar: String
)