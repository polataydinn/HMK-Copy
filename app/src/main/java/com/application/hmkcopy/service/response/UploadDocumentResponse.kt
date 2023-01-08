package com.application.hmkcopy.service.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class UploadDocumentResponse(
    @SerialName("key")
    val key: String? = "",
    @SerialName("url")
    val url: String? = "",
    val error: ApiCallError? = null
)