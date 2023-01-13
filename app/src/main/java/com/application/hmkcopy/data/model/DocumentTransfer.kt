package com.application.hmkcopy.data.model

import android.os.Parcelable
import com.application.hmkcopy.service.response.DocumentsResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentTransfer(
    val listOfDocuments: List<DocumentsResponse.Documents.Result>? = listOf()
): Parcelable
