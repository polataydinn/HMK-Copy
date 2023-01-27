package com.application.hmkcopy.service.response
import android.os.Parcelable
import com.application.hmkcopy.service.response.ApiCallError
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class VerifyCodeResponse(
    @SerialName("code")
    val code: Int? = 0,
    @SerialName("message")
    val message: String? = "",
    @SerialName("token")
    val token: String? = "",
    val error: ApiCallError? = null
) : Parcelable