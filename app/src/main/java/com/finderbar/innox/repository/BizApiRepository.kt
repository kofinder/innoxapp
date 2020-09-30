package com.finderbar.innox.repository

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*
import java.lang.reflect.Array

interface BizApiRepository {

    @POST("register")
    suspend fun register(@Body register: Register): Response<ServiceResponse<Token>>

    @POST("login")
    suspend fun login(@Body login: Login): Response<ServiceResponse<Token>>

    @GET("user")
    suspend fun getUserProfile(@Query("user_id") userId: Int): Response<ServiceResponse<User>>

    @GET("home")
    suspend fun getScreen(): Response<ServiceResponse<HomeScreen>>

    @GET("categorys")
    suspend fun getCategory(): Response<ServiceResponse<Categories>>

    @GET("product")
    suspend fun getProduct(@Query("product_id") productId: Int): Response<ServiceResponse<ProductDetail>>

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
    suspend fun getArtWorkCategory(): Response<ServiceResponse<ArtWorkCategories>>

    @GET("artworks_by_category")
    suspend fun getArtWorkByCategoryId(@Query("artwork_category_id") categoryId: Int): Response<ServiceResponse<ArtWorks>>

    @GET("artwork/designers")
    suspend fun getArtWorkDesigner(): Response<ServiceResponse<ArtWorkDesigners>>

    @GET("artworks_by_designer")
    suspend fun getArtWorkByDesignerId(@Query("designer_id") designerId: Int): Response<ServiceResponse<ArtWorks>>

    @GET("fonts")
    suspend fun getFonts(): Response<ServiceResponse<Fonts>>

    @GET("states")
    suspend fun getStates(): Response<ServiceResponse<States>>

    @GET("townships")
    suspend fun getTownships(@Query("state_id") stateId: Int): Response<ServiceResponse<TownShips>>

    @POST("shopping/cart")
    suspend fun saveShoppingCart(@Body cart: ShoppingCart): Response<ServiceResponse<Any>>

    @PUT("shopping/cart")
    suspend fun editShoppingCart(@Query("cart_id") cartId: Int, @Query("quantity") quantity: Int): Response<ServiceResponse<Any>>

    @DELETE("shopping/cart")
    suspend fun deleteShoppingCart(@Body cart: CartIds): Response<ServiceResponse<Any>>

    @GET("shopping/carts")
    suspend fun getShoppingCart(): Response<ServiceResponse<ShoppingCarts>>

    @POST("order/preload")
    suspend fun orderPreload(@Body cart: CartIds): Response<ServiceResponse<PreLoadOrder>>

}

///// REQUEST DATA MODEL ///////
@Keep
data class Login(
 val username: String,
 val password: String
)

@Keep
data class Register(
    val name: String,
    val email: String,
    val phoneNo: String,
    val password: String,
    val confirm_password: String,
    val state_id: Int,
    val township_id: Int,
    val detail_address: String,
    val profile_image: String,
    val user_role: Int = 2
)

@Keep
data class ShoppingCart(
    val product_id: Int,
    val color_id: Int,
    val size_id: Int,
    val quantity: Int
)

@Keep
data class CartIds(
    @SerializedName("cart_ids")
    val categoryIds: ArrayList<Int>
)

///// RESPONSE DATA MODEL ///////
@Keep
data class ServiceResponse<T>(
    @SerializedName("data") val data: T,
    @SerializedName("responseMessage") val responseMessage: String,
    @SerializedName("errorList") val error: ArrayList<Any>
)

@Keep
data class ApiError(
    @SerializedName("responseCode") val code: Int,
    @SerializedName("responseMessage") val msg: String
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
data class ArtWorkCategories(
    @SerializedName("artwork_categorys")
    val artWorkCategories: MutableList<ArtWorkCategory>? = mutableListOf()
)

@Keep
data class ArtWorkDesigners(
    @SerializedName("artwork_designers")
    val artWorkDesigners: MutableList<ArtWorkDesigner>? = mutableListOf()
)

@Keep
data class ArtWorks(
    @SerializedName("artworks")
    val artWorks: MutableList<ArtWork>? = mutableListOf()
)

@Keep
data class Fonts(
    @SerializedName("fonts")
    val fonts: MutableList<Font>? = mutableListOf()
)

@Keep
data class TownShips(
    @SerializedName("township_list")
    val townships: MutableList<TownShip>? = mutableListOf()
)

@Keep
data class States(
    @SerializedName("state_list")
    val states: MutableList<State>? = mutableListOf()
)

@Keep
data class PreLoadOrder(
    @SerializedName("total_cost") val totalCost: Int? = 0,
    @SerializedName("total_cost_text") val totalCostText: String? = "",
    @SerializedName("total_item_cost") val totalItemCost: Int? = 0,
    @SerializedName("total_item_cost_text") val totalItemCostText: String? = "",
    @SerializedName("delivery_cost") val deliveryCost: Int? = 0,
    @SerializedName("user_detail") val userDetail: UserDetail,
    @SerializedName("payment_types") val paymentType: MutableList<PaymentType>? = mutableListOf(),
    @SerializedName("order_items") val orderItem: MutableList<OrderItem>? = mutableListOf()
)

data class UserDetail(
    @SerializedName("user_id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("phone_no") val phoneNo: String? = "",
    @SerializedName("state_id") val stateId: Int? = 0,
    @SerializedName("township_id") val townShipId: Int? = 0
)

data class PaymentType (
    @SerializedName("payment_type_id") val id: Int? = 0,
    @SerializedName("name") val name: String? = "",
    @SerializedName("code") val code: String? = "",
    @SerializedName("is_offline") val offline: Int? = 0,
    @SerializedName("payment_image") val image: String? = ""
)

data class OrderItem(
    @SerializedName("cart_id") val cartId: Int? = 0,
    @SerializedName("product_id") val productId: Int? = 0,
    @SerializedName("product_name") val name: String? = "",
    @SerializedName("unit_price") val unitPrice: Int? = 0,
    @SerializedName("unit_price_text") val unitPriceText: String? = "",
    @SerializedName("quantity") val quantity: Int? = 0,
    @SerializedName("sub_total") val subTotal: Int? = 0,
    @SerializedName("image_path") val image: String? = ""
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
    @SerializedName("images") val images: MutableList<String>? = mutableListOf(),
    @SerializedName("price_text") val price: String? = "",
    @SerializedName("origninal_price_text") val originalPrice: String? = "",
    @SerializedName("discount_percentage_text") val discount: String? = "",
    @SerializedName("is_promotion") val isPromotion: Boolean? = false,
    @SerializedName("is_popular") val isPopular: Boolean? = false,
    @SerializedName("is_new_arrival") val isNewArrival: Boolean? = false,
    @SerializedName("detail") val description: String? = "",
    @SerializedName("color_list") val colors: MutableList<Color>? = mutableListOf(),
    @SerializedName("size_list") val sizes: MutableList<Size>? = mutableListOf()
)

@Keep
data class Color(
    @SerializedName("color_id") val id: Int,
    @SerializedName("color_name") val name: String,
    @SerializedName("color_code") val code: String
)

@Keep
data class Size(
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
    @SerializedName("categroy_id") val id: Int,
    @SerializedName("category_name") val name: String,
    @SerializedName("category_code") val code: String
)

@Keep
data class ArtWorkDesigner(
    @SerializedName("designer_id") val id: Int,
    @SerializedName("designer_name") val name: String,
    @SerializedName("designer_avatar") val imageAvatar: String
)

@Keep
data class Font(
    @SerializedName("font_id") val id: Int,
    @SerializedName("font_sample") val name: String,
    @SerializedName("font_url") val uri: String
)

@Keep
data class State(
    @SerializedName("state_id") val id: Int,
    @SerializedName("state_name") val name: String
)

@Keep
data class TownShip(
    @SerializedName("township_id") val id: Int,
    @SerializedName("township_name") val name: String
)

@Keep
data class Token(
    @SerializedName("jwt_token")
    val jwtToken: String? = "",
    @SerializedName("user_id")
    val userId: String? = ""
)

@Keep
data class User(
    @SerializedName("user_id")
    val id: String? = "",
    @SerializedName("user_name")
    val name: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("phoneNo")
    val phoneNo: String? = "",
    @SerializedName("user_role_level_text")
    val role: String? = "",
    @SerializedName("profile_image")
    val image: String? = "",
    @SerializedName("state_id")
    val state: String? = "",
    @SerializedName("township_id")
    val township: String? = "",
    @SerializedName("address")
    val address: String? = ""
)

@Keep
data class ShoppingCarts(
    @SerializedName("total_amount")
    val totalAmount: Float? = 0.0f,
    @SerializedName("total_amount_text")
    val totalAmountText: String? = "",
    @SerializedName("shopping_carts")
    val carts: MutableList<Cart>? = mutableListOf()
)

@Keep
data class Cart(
    @SerializedName("cart_id")
    val id: Int? = 0,
    @SerializedName("product_name")
    val name: String? = "",
    @SerializedName("product_price")
    val price: Float? = 0f,
    @SerializedName("product_price_text")
    val priceText: String? = "",
    @SerializedName("quantity")
    val quantity: Int? = 0,
    @SerializedName("product_sub_total")
    val subTotal: Float? = 0f,
    @SerializedName("product_sub_total_text")
    val subTotalText: String? = "",
    @SerializedName("product_image")
    val image: String? = "",
    var isCheck: Boolean = false
)