package com.application.hmkcopy.base

import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.navigation.NavigationCommand
import com.application.hmkcopy.presentation.home.copy_center.adapter.PrintViewOptions
import com.application.hmkcopy.service.ErrorModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    private val _navigation = MutableSharedFlow<NavigationCommand>()
    val navigation = _navigation.asSharedFlow()
    var errorMessage = MutableLiveData<Event<ErrorModel>>()

    var successMessage = MutableLiveData<Event<String>>()

    private val _progress = MutableLiveData<Event<Boolean>>()
    val progress: LiveData<Event<Boolean>>
        get() = _progress

    fun navigate(navDirections: NavDirections) {
        viewModelScope.launch {
            _navigation.emit(NavigationCommand.ToDirection(navDirections))
        }
    }

    fun navigate(uri: Uri) {
        viewModelScope.launch {
            _navigation.emit(NavigationCommand.ToUri(uri))
        }
    }

    fun navigate(navigationCommand: NavigationCommand) {
        viewModelScope.launch {
            _navigation.emit(navigationCommand)
        }
    }

    fun navigateToDirectionId(directionId: Int, bundle: Bundle? = null) {
        viewModelScope.launch {
            _navigation.emit(NavigationCommand.ToId(directionId, bundle))
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigation.emit(NavigationCommand.Back)
        }
    }

    fun toggleProgress(show: Boolean) {
        _progress.postValue(Event(show))
    }
}