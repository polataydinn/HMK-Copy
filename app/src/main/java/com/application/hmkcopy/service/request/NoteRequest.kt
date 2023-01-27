package com.application.hmkcopy.service.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    @SerialName("note")
    val note: String? = ""
)