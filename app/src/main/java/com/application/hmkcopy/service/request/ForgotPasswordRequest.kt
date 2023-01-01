package com.application.hmkcopy.service.request


import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @SerializedName("phone")
    val phone: String = ""
)