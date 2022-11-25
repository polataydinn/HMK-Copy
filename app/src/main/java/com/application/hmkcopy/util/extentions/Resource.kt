package com.application.hmkcopy.util.extentions

import com.application.hmkcopy.presentation.MainActivity

fun appString(id: Int): String {
    return MainActivity.instance?.getString(id) ?: ""
}
