package com.application.hmkcopy.service.response


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class UploadDocumentResponse(
    @SerializedName("code")
    val code: Int? = 0,
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("presignedUrl")
    val presignedUrl: PresignedUrl? = PresignedUrl(),
    val error: ApiCallError? = null
)
data class PresignedUrl(
    @SerializedName("key")
    val key: String? = "",
    @SerializedName("url")
    val url: String? = ""
)