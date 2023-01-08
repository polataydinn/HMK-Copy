package com.application.hmkcopy.util.extentions

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


fun BottomSheetDialogFragment.show(context: Context) {
    (context as? FragmentActivity)?.supportFragmentManager?.let {
        show(it, tag)
    }
}