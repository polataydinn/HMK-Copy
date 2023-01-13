package com.application.hmkcopy.util.extentions

import android.view.View
import androidx.core.view.isVisible

fun View.invertVisibility() {
    isVisible = isVisible.not()
}