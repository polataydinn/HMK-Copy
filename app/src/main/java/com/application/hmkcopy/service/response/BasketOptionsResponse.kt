package com.application.hmkcopy.service.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasketOptionsResponse(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("printOptions")
    val printOptions: List<PrintOption> = listOf(),
    val apiCallError: ApiCallError? = null
) : Parcelable {
    @Parcelize
    data class PrintOption(
        @SerializedName("isDoubleSided")
        val isDoubleSided: Boolean = false,
        @SerializedName("isDoubleSidedColored")
        val isDoubleSidedColored: Boolean = false,
        @SerializedName("isOneSidedColored")
        val isOneSidedColored: Boolean = false,
        @SerializedName("size")
        val size: String = ""
    ) : Parcelable
}