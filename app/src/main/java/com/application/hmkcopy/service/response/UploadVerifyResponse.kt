package com.application.hmkcopy.service.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class UploadVerifyResponse(
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
) : Parcelable

