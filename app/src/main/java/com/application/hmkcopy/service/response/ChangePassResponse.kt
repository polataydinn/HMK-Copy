package com.application.hmkcopy.service.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePassResponse(
    @SerialName("code")
    val code: Int? = 0,
    @SerialName("message")
    val message: String? = "",
    val apiCallError: ApiCallError? = null
)