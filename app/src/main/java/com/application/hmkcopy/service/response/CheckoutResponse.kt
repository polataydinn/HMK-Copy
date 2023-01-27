package com.application.hmkcopy.service.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class CheckoutResponse(
    @SerializedName("checkout")
    val checkout: Checkout = Checkout(),
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = "",
    val apiCallError: ApiCallError? = null
) {
    @Serializable
    data class Checkout(
        @SerialName("active")
        val active: Boolean? = false,
        @SerialName("id")
        val id: String? = "",
        @SerialName("isMobile")
        val isMobile: Boolean? = false,
        @SerialName("products")
        val products: List<Product?> = listOf(),
        @SerialName("status")
        val status: String? = "",
        @SerialName("totalAmount")
        val totalAmount: Int? = 0,
        @SerialName("user")
        val user: String? = ""
    ) {
        @Serializable
        data class Product(
            @SerialName("document")
            val document: Document? = Document(),
            @SerialName("id")
            val id: String? = "",
            @SerialName("pageCount")
            val pageCount: Int? = 0,
            @SerialName("priceInfoText")
            val priceInfoText: List<PriceInfoText?>? = listOf(),
            @SerialName("prices")
            val prices: Prices? = Prices(),
            @SerialName("printOptions")
            val printOptions: PrintOptions? = PrintOptions(),
            @SerialName("printingPageCount")
            val printingPageCount: Int? = 0
        ) {
            @Serializable
            data class Document(
                @SerialName("accessUsers")
                val accessUsers: List<Any?>? = listOf(),
                @SerialName("documentNumber")
                val documentNumber: String? = "",
                @SerialName("id")
                val id: String? = "",
                @SerialName("key")
                val key: String? = "",
                @SerialName("name")
                val name: String? = "",
                @SerialName("pageCount")
                val pageCount: Int? = 0,
                @SerialName("uploadDate")
                val uploadDate: String? = "",
                @SerialName("user")
                val user: String? = ""
            )

            @Serializable
            data class PriceInfoText(
                @SerialName("_id")
                val id: String? = "",
                @SerialName("key")
                val key: Int? = 0,
                @SerialName("name")
                val name: String? = "",
                @SerialName("price")
                val price: Double? = 0.0
            )

            @Serializable
            data class Prices(
                @SerialName("coloredPrice")
                val coloredPrice: Double? = 0.0,
                @SerialName("price")
                val price: Double? = 0.0,
                @SerialName("spiralledPrice")
                val spiralledPrice: Double? = 0.0
            )

            @Parcelize
            data class PrintOptions(
                @SerialName("isColored")
                val isColored: Boolean? = false,
                @SerialName("isCovered")
                val isCovered: Boolean? = false,
                @SerialName("isSpiralled")
                val isSpiralled: Boolean? = false,
                @SerialName("paperDirection")
                val paperDirection: String? = "",
                @SerialName("paperSide")
                val paperSide: Int? = 0,
                @SerialName("paperSize")
                val paperSize: String? = "",
                @SerialName("paperSquare")
                val paperSquare: Int? = 0
            ) : Parcelable
        }


    }
}