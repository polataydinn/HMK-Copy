package com.application.hmkcopy.service.response


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("tokens")
    val tokens: Tokens = Tokens(),
    @SerializedName("user")
    val user: User = User(),
    val error: ApiCallError? = null
)

data class Tokens(
    @SerializedName("access")
    val access: Access = Access(),
    @SerializedName("refresh")
    val refresh: Refresh = Refresh()
)

data class Access(
    @SerializedName("expires")
    val expires: String = "",
    @SerializedName("token")
    val token: String = ""
)

data class Refresh(
    @SerializedName("expires")
    val expires: String = "",
    @SerializedName("token")
    val token: String = ""
)

data class User(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("phone")
    val phone: Long = 0,
    @SerializedName("role")
    val role: String = ""
)