package com.application.hmkcopy.service.response

import com.google.gson.annotations.SerializedName

data class ApiCallError(
    @SerializedName("code")
    val code: String = "",
    @SerializedName("message")
    val message: String = ""
)