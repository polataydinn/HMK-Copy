package com.application.hmkcopy.service.request


import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("name")
    val name: String = "",
    @SerializedName("password")
    val password: String = "",
    @SerializedName("phone")
    val phone: String = ""
)