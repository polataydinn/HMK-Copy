package com.application.hmkcopy.service.response

import kotlinx.serialization.SerialName

data class UploadResponseUser(
    @SerialName("id")
    val id: String? = "",
    @SerialName("isEmailVerified")
    val isEmailVerified: Boolean? = false,
    @SerialName("isPhoneVerified")
    val isPhoneVerified: Boolean? = false,
    @SerialName("isSeller")
    val isSeller: Boolean? = false,
    @SerialName("name")
    val name: String? = "",
    @SerialName("phone")
    val phone: String? = "",
    @SerialName("registerDate")
    val registerDate: String? = "",
    @SerialName("role")
    val role: String? = ""
)
