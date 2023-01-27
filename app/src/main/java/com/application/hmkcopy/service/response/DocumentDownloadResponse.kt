package com.application.hmkcopy.service.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class DocumentDownloadResponse(
    @SerialName("code")
    val code: Int? = 0,
    @SerialName("message")
    val message: String? = "",
    @SerialName("presignedUrl")
    val presignedUrl: String? = "",
    val error: ApiCallError? = null
) : Parcelable