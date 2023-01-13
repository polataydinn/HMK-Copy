package com.application.hmkcopy.service.request


import com.google.gson.annotations.SerializedName

data class CreateCheckoutBasketRequest(
    @SerializedName("documents")
    val documents: List<String> = listOf()
)