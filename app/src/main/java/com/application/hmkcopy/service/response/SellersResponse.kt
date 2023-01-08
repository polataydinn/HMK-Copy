package com.application.hmkcopy.service.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class SellersResponse(
    val apiCallError: ApiCallError? = null
) : ArrayList<SellersResponseItem>()

@Parcelize
data class SellersResponseItem(
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("address")
    val address: String? = "",
    @SerialName("addressCoordinates")
    val addressCoordinates: String? = "",
    @SerialName("code")
    val code: String? = "",
    @SerialName("email")
    val email: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("name")
    val name: String? = "",
    @SerialName("personName")
    val personName: String? = "",
    @SerialName("phone")
    val phone: String? = "",
    @SerialName("prices")
    val prices: Prices? = Prices(),
    @SerialName("registerDate")
    val registerDate: String? = "",
    @SerialName("summary")
    val summary: Summary? = Summary(),
) : Parcelable

@Parcelize
data class Prices(
    @SerialName("additional")
    val additional: List<Additional?>? = emptyList(),
    @SerialName("paper")
    val paper: List<Paper?>? = emptyList()
) : Parcelable

@Parcelize
data class Additional(
    @SerialName("_id")
    val id: String? = "",
    @SerialName("name")
    val name: String? = "",
    @SerialName("pageMax")
    val pageMax: Int? = 0,
    @SerialName("pageMin")
    val pageMin: Int? = 0,
    @SerialName("price")
    val price: Int? = 0
) : Parcelable

@Parcelize
data class Paper(
    @SerialName("color")
    val color: String? = "",
    @SerialName("_id")
    val id: String? = "",
    @SerialName("price")
    val price: Double? = 0.0,
    @SerialName("side")
    val side: String? = "",
    @SerialName("size")
    val size: String? = ""
) : Parcelable

@Parcelize
data class Summary(
    @SerialName("cancelledOrders")
    val cancelledOrders: Int? = 0,
    @SerialName("completedOrders")
    val completedOrders: Int? = 0,
    @SerialName("totalOrders")
    val totalOrders: Int? = 0,
    @SerialName("totalPageCount")
    val totalPageCount: Int? = 0,
    @SerialName("totalPrice")
    val totalPrice: Int? = 0,
    @SerialName("totalPrintedPageCount")
    val totalPrintedPageCount: Int? = 0,
    @SerialName("waitingOrders")
    val waitingOrders: Int? = 0
): Parcelable

