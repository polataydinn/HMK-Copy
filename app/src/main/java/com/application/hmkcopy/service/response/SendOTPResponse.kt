package com.application.hmkcopy.service.response


import com.google.gson.annotations.SerializedName

data class SendOTPResponse(
    @SerializedName("token")
    val token: String = "",
    val error: ApiCallError? = null
)