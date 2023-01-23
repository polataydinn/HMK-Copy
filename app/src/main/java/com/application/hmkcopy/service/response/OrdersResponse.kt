package com.application.hmkcopy.service.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

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
        val products: List<String?>? = listOf(),
        @SerialName("seller")
        val seller: String? = "",
        @SerialName("status")
        val status: String? = "",
        @SerialName("totalAmount")
        val totalAmount: Int? = 0,
        @SerialName("user")
        val user: String? = ""
    ) : Parcelable
}