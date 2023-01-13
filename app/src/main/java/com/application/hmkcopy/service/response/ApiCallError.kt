package com.application.hmkcopy.service.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiCallError(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("message")
    val message: String = ""
) : Parcelable