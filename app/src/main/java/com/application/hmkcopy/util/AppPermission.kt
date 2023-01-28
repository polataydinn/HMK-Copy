package com.application.hmkcopy.util

import android.Manifest.permission.*
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.application.hmkcopy.presentation.home.MainActivity

class AppPermission {
    companion object {
        const val REQUEST_PERMISSION: Int = 123
        fun permissionGranted(context: Context) =
            ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION) == PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context, CAMERA) == PERMISSION_GRANTED


        fun requestPermission(activity: MainActivity) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA),
                REQUEST_PERMISSION
            )
        }
    }
}