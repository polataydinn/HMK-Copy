package com.application.hmkcopy.service.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class UploadVerifyResponse(
    @SerialName("accessUsers")
    val accessUsers: List<Any?>? = emptyList(),
    @SerialName("documentNumber")
    val documentNumber: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("key")
    val key: String? = "",
    @SerialName("name")
    val name: String? = "",
    @SerialName("pageCount")
    val pageCount: Int? = 0,
    @SerialName("uploadDate")
    val uploadDate: String? = "",
    @SerialName("user")
    val user: UploadResponseUser? = UploadResponseUser(),
    val apiCallError: ApiCallError? = null
)

