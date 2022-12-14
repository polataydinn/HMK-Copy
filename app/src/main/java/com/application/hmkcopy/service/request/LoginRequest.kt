package com.application.hmkcopy.service.request


import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("password")
    val password: String = "",
    @SerializedName("phone")
    val phone: String = ""
)