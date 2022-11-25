package com.application.hmkcopy.util.extentions

import android.content.Context

object SizeUtils {
    fun int2dp(context: Context, size: Int): Int {
        val scale: Float = context.resources.displayMetrics.density
        return (size * scale + 0.5f).toInt()
    }
}
