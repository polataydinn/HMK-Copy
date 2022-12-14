package com.application.hmkcopy.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavDirections


sealed class NavigationCommand {
    data class ToDirection(val directions: NavDirections) : NavigationCommand()
    data class ToId(val directionId: Int, val bundle: Bundle?) : NavigationCommand()
    data class ToUri(val uri: Uri): NavigationCommand()
    data class ToActivity<T>(val activity: Class<T>) : NavigationCommand()
    object Back : NavigationCommand()
}