package com.application.hmkcopy.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.repository.user.UserHelper
import com.application.hmkcopy.repository.user.UserRepository
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.service.request.ChangePassRequest
import com.application.hmkcopy.service.response.ChangePassResponse
import com.application.hmkcopy.service.response.UserInfoResponse
import com.application.hmkcopy.util.SharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
): BaseViewModel() {

    private val _userData : MutableLiveData<UserInfoResponse> = MutableLiveData()
    val userData : LiveData<UserInfoResponse> get() = _userData


    fun getUserData(){
        viewModelScope.launch {
            val response = repository.getUserData()
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                _userData.postValue(response)
            }
        }
    }

    fun logout() {
        UserHelper.tokens = null
    }

    fun resetPassword(oldPass: String?, newPass: String?, newPassRepeat: String?) {
        if (oldPass?.isEmpty() == true || newPass?.isEmpty() == true || newPassRepeat?.isEmpty() == true) {
            errorMessage.value = Event(ErrorModel(message = "Lütfen bütün alanları doldurunuz"))
            return
        }
        if (newPass != newPassRepeat){
            errorMessage.value = Event(ErrorModel(message = "Girilen şifreler uyuşmuyor"))
            return
        }
        toggleProgress(true)
        viewModelScope.launch {
            val response = repository.changePassword(ChangePassRequest(oldPassword = oldPass, newPassword = newPass))
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                successMessage.value = Event(response.message)
            }
            toggleProgress(false)
        }
    }
}