package com.application.hmkcopy.service.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerPatchResponse(
    @SerializedName("checkout")
    val checkout: CheckoutSeller = CheckoutSeller(),
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = "",
    val apiCallError: ApiCallError? = null
) : Parcelable

@Parcelize
data class CheckoutSeller(
    @SerializedName("active")
    val active: Boolean = false,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("isMobile")
    val isMobile: Boolean = false,
    @SerializedName("products")
    val products: List<String> = listOf(),
    @SerializedName("seller")
    val seller: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("user")
    val user: String = ""
) : Parcelable