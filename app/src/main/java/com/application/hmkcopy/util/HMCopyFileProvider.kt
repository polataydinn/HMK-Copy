package com.application.hmkcopy.util

import androidx.core.content.FileProvider
import com.application.hmkcopy.BuildConfig

class HMCopyFileProvider : FileProvider() {
    companion object {
        const val AUTHORITY = BuildConfig.APPLICATION_ID + ".util.HMCopyFileProvider"
        const val PATH_PICTURES = "Pictures"
        const val PATH_DOCUMENTS = "Documents"
    }
}