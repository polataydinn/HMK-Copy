package com.application.hmkcopy.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.ZoneId

@Parcelize
data class OrderListItem(
    val id: Int = 0,
    val uploadDate: Long? = 0L,
    val pageSize: Int? = 0,
    val documentType: String? = "",
    val lessonName: String? = "",
    val isItemSelected: Boolean = false
) : Parcelable