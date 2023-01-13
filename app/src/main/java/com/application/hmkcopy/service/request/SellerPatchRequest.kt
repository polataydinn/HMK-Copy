package com.application.hmkcopy.service.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerPatchRequest(
    @SerializedName("sellerId")
    val sellerId: String = ""
) : Parcelable