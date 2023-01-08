package com.application.hmkcopy.service.request

import com.google.gson.annotations.SerializedName

data class UploadDocumentRequest(
    @SerializedName("name")
    val name: String = "",
)
