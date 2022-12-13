package com.application.hmkcopy.service.request


import com.google.gson.annotations.SerializedName

data class VerifyPhoneRequest(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("token")
    val token: String = ""
)