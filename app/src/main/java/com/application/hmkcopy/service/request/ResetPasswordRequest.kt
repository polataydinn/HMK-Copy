package com.application.hmkcopy.service.request


import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("password")
    val password: String = "",
    @SerializedName("token")
    val token: String = ""
)