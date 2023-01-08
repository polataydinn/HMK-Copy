package com.application.hmkcopy.service.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class UploadVerifyRequest(
    @SerialName("key")
    var key: String? = "",
    @SerialName("name")
    var name: String? = ""
)