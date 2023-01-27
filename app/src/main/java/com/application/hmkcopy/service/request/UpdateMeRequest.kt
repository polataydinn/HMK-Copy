package com.application.hmkcopy.service.request


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class UpdateMeRequest(
    @SerialName("email")
    val email: String? = "",
    @SerialName("name")
    val name: String? = ""
) : Parcelable