package com.application.hmkcopy.service.response


import com.google.gson.annotations.SerializedName

data class CreateCheckoutBasketResponse(
    @SerializedName("checkout")
    val checkout: Checkout = Checkout(),
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = "",
    val apiCallError: ApiCallError? = null
)

data class Checkout(
    @SerializedName("active")
    val active: Boolean = false,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("isMobile")
    val isMobile: Boolean = false,
    @SerializedName("products")
    val products: List<Product> = listOf(),
    @SerializedName("status")
    val status: String = "",
    @SerializedName("user")
    val user: String = ""
)

data class Product(
    @SerializedName("document")
    val document: String = "",
    @SerializedName("id")
    val id: String = "",
    @SerializedName("pageCount")
    val pageCount: Int = 0,
    @SerializedName("priceInfoText")
    val priceInfoText: List<Any> = listOf(),
    @SerializedName("prices")
    val prices: PricesCheckout = PricesCheckout(),
    @SerializedName("printOptions")
    val printOptions: PrintOptions = PrintOptions()
)

data class PricesCheckout(
    @SerializedName("coloredPrice")
    val coloredPrice: Int = 0,
    @SerializedName("price")
    val price: Int = 0,
    @SerializedName("spiralledPrice")
    val spiralledPrice: Int = 0
)

data class PrintOptions(
    @SerializedName("isColored")
    val isColored: Boolean = false,
    @SerializedName("isCovered")
    val isCovered: Boolean = false,
    @SerializedName("isSpiralled")
    val isSpiralled: Boolean = false,
    @SerializedName("paperDirection")
    val paperDirection: String = "",
    @SerializedName("paperSide")
    val paperSide: Int = 0,
    @SerializedName("paperSize")
    val paperSize: String = "",
    @SerializedName("paperSquare")
    val paperSquare: Int = 0
)