package com.application.hmkcopy.service.request

import com.google.gson.annotations.SerializedName

data class UpdateBasketRequest(
    @SerializedName("isColored")
    val isColored: Boolean = false,
    @SerializedName("isCovered")
    val isCovered: Boolean = false,
    @SerializedName("isSpiralled")
    val isSpiralled: Boolean = false,
    @SerializedName("paperDirection")
    val paperDirection: String = "",
    @SerializedName("paperSide")
    val paperSide: Int = 0,
    @SerializedName("paperSize")
    val paperSize: String = "",
    @SerializedName("paperSquare")
    val paperSquare: Int = 0
)