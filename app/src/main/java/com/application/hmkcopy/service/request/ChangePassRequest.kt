package com.application.hmkcopy.service.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePassRequest(
    @SerialName("newPassword")
    val newPassword: String? = "",
    @SerialName("oldPassword")
    val oldPassword: String? = ""
)