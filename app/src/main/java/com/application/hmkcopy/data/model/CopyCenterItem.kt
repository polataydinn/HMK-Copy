package com.application.hmkcopy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CopyCenterItem(
    val id: Int? = 0,
    val centerName: String? = "",
    val centerAddress: String? = ""
) : Parcelable