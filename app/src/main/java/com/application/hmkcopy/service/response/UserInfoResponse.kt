package com.application.hmkcopy.service.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
data class UserInfoResponse(
    @SerialName("code")
    val code: Int? = 0,
    @SerialName("message")
    val message: String? = "",
    @SerialName("user")
    val user: User? = User(),
    val apiCallError: ApiCallError? = null
) : Parcelable {
    @Parcelize
    data class User(
        @SerialName("address")
        val address: String? = "",
        @SerialName("avatar")
        val avatar: Avatar? = Avatar(),
        @SerialName("email")
        val email: String? = "",
        @SerialName("id")
        val id: String? = "",
        @SerialName("isEmailVerified")
        val isEmailVerified: Boolean? = false,
        @SerialName("isPhoneVerified")
        val isPhoneVerified: Boolean? = false,
        @SerialName("isSeller")
        val isSeller: Boolean? = false,
        @SerialName("name")
        val name: String? = "",
        @SerialName("phone")
        val phone: String? = "",
        @SerialName("registerDate")
        val registerDate: String? = "",
        @SerialName("role")
        val role: String? = ""
    ) : Parcelable {
        @Parcelize
        data class Avatar(
            @SerialName("url")
            val url: String? = ""
        ) : Parcelable
    }
}