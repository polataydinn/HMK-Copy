package com.application.hmkcopy.service.response


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import kotlinx.serialization.SerialName

data class DocumentsResponse(
    @SerialName("documents")
    val documents: Documents? = Documents(),
    @SerialName("message")
    val message: String? = "",
    val apiCallError: ApiCallError? = null
) {
    data class Documents(
        @SerialName("limit")
        val limit: Int? = 0,
        @SerialName("page")
        val page: Int? = 0,
        @SerialName("results")
        val results: List<Result?>? = emptyList(),
        @SerialName("totalPages")
        val totalPages: Int? = 0,
        @SerialName("totalResults")
        val totalResults: Int? = 0
    ) {
        @Parcelize
        data class Result(
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
            val user: String? = "",
            val isItemSelected : Boolean = false
        ): Parcelable
    }
}