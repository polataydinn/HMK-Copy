package com.application.hmkcopy.util.extentions

import androidx.core.text.isDigitsOnly

fun String.isValidPhone(): Boolean {
    return this.length == 10 && this.isDigitsOnly()
}