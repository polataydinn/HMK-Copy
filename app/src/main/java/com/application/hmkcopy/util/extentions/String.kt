package com.application.hmkcopy.util.extentions

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat

fun String.toSquaredPaper(): Int {
    return when (this) {
        "1x1 Sayfa" -> 1
        "1x2 Sayfa" -> 2
        "1x4 Sayfa" -> 4
        else -> 1
    }
}

fun View.backgroundColor(int: Int) {
    setBackgroundColor(mainColor(this.context, int))
}

fun mainColor(context: Context, id: Int): Int {
    return ContextCompat.getColor(context, id)
}

fun delay(time: Long, callback: Callback) {
    Handler(Looper.getMainLooper()).postDelayed({
        callback.invoke()
    }, time)
}