package com.application.hmkcopy.presentation.authentication

import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.repository.user.UserHelper
import com.application.hmkcopy.repository.user.UserRepository
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.util.extentions.isValidPhone
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private var otpToken: String = ""

    fun onRegisterPhoneNumber(phoneNumber: String) {
        if (phoneNumber.isValidPhone()) {
            UserHelper.phoneNumber = phoneNumber
            navigate(EnterPhoneFragmentDirections.toRegisterWithPhoneNumberFragment())
        }
    }

    fun register(nameAndSurname: String, password: String) {
        if (nameAndSurname.isEmpty()) {
            errorMessage.value = Event(ErrorModel(message = "Lütfen Adınızı giriniz"))
            return
        }
        if (password.isEmpty()) {
            errorMessage.value = Event(ErrorModel(message = "Lütfen Bir şifre giriniz"))
            return
        }
        viewModelScope.launch {
            val registerResponse =
                userRepository.registerUser(UserHelper.phoneNumber, nameAndSurname, password)
            registerResponse?.let { response ->
                if(response.error != null) {
                    errorMessage.value = Event(ErrorModel(message = response.error.message))
                } else  {
                    UserHelper.user = response.user
                    UserHelper.tokens = response.tokens
                    navigate(RegisterNamePassFragmentDirections.toOTPFragment())
                }
            }
        }
    }

    fun requestOTP() {
        viewModelScope.launch {
            val otpResponse = userRepository.requestOTP()
            otpResponse?.let { response ->
                if(response.error != null) {
                    errorMessage.value = Event(ErrorModel(message = response.error.message))
                } else  {
                    otpToken = response.token
                }
            }
        }
    }

    fun checkOtp(pin: String) {
        if (pin.length != 5) {
            errorMessage.value = Event(ErrorModel(message = "Doğrulama kodunu kontrol ediniz."))
            return
        }
        viewModelScope.launch {
            val checkResponse = userRepository.checkOTP(code = pin, token = otpToken)
            if(checkResponse != null) {
                errorMessage.value = Event(ErrorModel(message = checkResponse.message))
            } else {
                navigate(OTPinFragmentDirections.toAccountCreatedFragment())
            }
        }
    }

    fun resendOtp() {
        requestOTP()
    }
}