package com.application.hmkcopy.service.response

import android.os.Parcelable
import com.application.hmkcopy.service.response.ApiCallError
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class OrdersResponse(
    @SerialName("code")
    val code: Int? = 0,
    @SerialName("message")
    val message: String? = "",
    @SerialName("orders")
    val orders: List<Order?>? = listOf(),
    val apiCallError: ApiCallError? = null
) : Parcelable {
    @Parcelize
    data class Order(
        @SerialName("active")
        val active: Boolean? = false,
        @SerialName("id")
        val id: String? = "",
        @SerialName("orderDate")
        val orderDate: String? = "",
        @SerialName("orderNumber")
        val orderNumber: String? = "",
        @SerialName("pageCount")
        val pageCount: Int? = 0,
        @SerialName("payment")
        val payment: String? = "",
        @SerialName("printedPageCount")
        val printedPageCount: Int? = 0,
        @SerialName("products")
        val products: List<Product?>? = listOf(),
        @SerialName("seller")
        val seller: Seller? = Seller(),
        @SerialName("status")
        val status: Status? = Status(),
        @SerialName("totalAmount")
        val totalAmount: Int? = 0,
        @SerialName("user")
        val user: String? = ""
    ) : Parcelable {
        @Parcelize
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
            val printingPageCount: Int? = 0,
            var status: Status? = Status(),
            var seller: Seller? = Seller()
        ) : Parcelable {
            @Parcelize
            data class Document(
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
            ) : Parcelable

            @Parcelize
            data class PriceInfoText(
                @SerialName("_id")
                val id: String? = "",
                @SerialName("key")
                val key: Int? = 0,
                @SerialName("name")
                val name: String? = "",
                @SerialName("price")
                val price: Int? = 0
            ) : Parcelable

            @Parcelize
            data class Prices(
                @SerialName("coloredPrice")
                val coloredPrice: Int? = 0,
                @SerialName("price")
                val price: Int? = 0,
                @SerialName("spiralledPrice")
                val spiralledPrice: Int? = 0
            ) : Parcelable

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

        @Parcelize
        data class Seller(
            @SerialName("id")
            val id: String? = "",
            @SerialName("name")
            val name: String? = ""
        ) : Parcelable

        @Parcelize
        data class Status(
            @SerialName("color")
            val color: String? = "",
            @SerialName("detail")
            val detail: String? = "",
            @SerialName("miniSummary")
            val miniSummary: String? = "",
            @SerialName("summary")
            val summary: String? = ""
        ) : Parcelable
    }
}