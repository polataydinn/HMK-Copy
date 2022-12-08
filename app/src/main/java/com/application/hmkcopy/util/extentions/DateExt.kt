package com.application.hmkcopy.util.extentions

import java.text.SimpleDateFormat
import java.util.*

fun Long.getDateTime(): String? {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val netDate = Date(this * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}