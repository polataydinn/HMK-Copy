package com.application.hmkcopy.service.response
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CheckoutResponse(
    @SerializedName("checkout")
    val checkout: Checkout = Checkout(),
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = "",
    val apiCallError: ApiCallError? = null
) {
    data class Checkout(
        @SerializedName("active")
        val active: Boolean = false,
        @SerializedName("id")
        val id: String = "",
        @SerializedName("isMobile")
        val isMobile: Boolean = false,
        @SerializedName("products")
        val products: List<Product> = listOf(),
        @SerializedName("seller")
        val seller: String = "",
        @SerializedName("status")
        val status: String = "",
        @SerializedName("totalAmount")
        val totalAmount: Double = 0.0,
        @SerializedName("user")
        val user: String = ""
    ) {
        data class Product(
            @SerializedName("document")
            val document: Document = Document(),
            @SerializedName("id")
            val id: String = "",
            @SerializedName("pageCount")
            val pageCount: Int = 0,
            @SerializedName("priceInfoText")
            val priceInfoText: List<PriceInfoText> = listOf(),
            @SerializedName("prices")
            val prices: Prices = Prices(),
            @SerializedName("printOptions")
            val printOptions: PrintOptions = PrintOptions(),
            @SerializedName("printingPageCount")
            val printingPageCount: Int = 0
        ) {
            data class Document(
                @SerializedName("accessUsers")
                val accessUsers: List<Any> = listOf(),
                @SerializedName("documentNumber")
                val documentNumber: String = "",
                @SerializedName("id")
                val id: String = "",
                @SerializedName("key")
                val key: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("pageCount")
                val pageCount: Int = 0,
                @SerializedName("user")
                val user: String = ""
            )

            data class PriceInfoText(
                @SerializedName("_id")
                val id: String = "",
                @SerializedName("name")
                val name: String = "",
                @SerializedName("price")
                val price: Double = 0.0
            )

            data class Prices(
                @SerializedName("coloredPrice")
                val coloredPrice: Double = 0.0,
                @SerializedName("price")
                val price: Double = 0.0,
                @SerializedName("spiralledPrice")
                val spiralledPrice: Double = 0.0
            )
            @Parcelize
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
            ) : Parcelable
        }
    }
}