package com.application.hmkcopy.presentation.authentication

import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.navigation.NavigationCommand
import com.application.hmkcopy.presentation.home.MainActivity
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
            if (phoneNumber.isEmpty()) {
                errorMessage.value = Event(ErrorModel(message = "Lütfen telefon numarası giriniz"))
                return
            }
            UserHelper.phoneNumber = phoneNumber
            navigate(EnterPhoneFragmentDirections.toRegisterWithPhoneNumberFragment())
        }else {
            errorMessage.value = Event(ErrorModel(message = "Lütfen 10 haneli numara giriniz"))
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
        toggleProgress(true)
        viewModelScope.launch {
            val userResponse =
                userRepository.registerUser(UserHelper.phoneNumber, nameAndSurname, password)
            if (userResponse.error != null) {
                errorMessage.value = Event(ErrorModel(message = userResponse.error.message))
            } else {
                UserHelper.user = userResponse.user
                UserHelper.tokens = userResponse.tokens
                navigate(RegisterNamePassFragmentDirections.toOTPFragment())
            }
            toggleProgress(false)
        }
    }

    fun requestOTP(phoneNumber: String? = null) {
        if (phoneNumber == null) sendVerificationCodePhone()
    }

    private fun sendVerificationCodePhone() {
        toggleProgress(true)
        viewModelScope.launch {
            val otpResponse = userRepository.sendVerificationCodePhone()
            if (otpResponse.error != null) {
                errorMessage.value = Event(ErrorModel(message = otpResponse.error.message))
            } else {
                otpToken = otpResponse.token
            }
            toggleProgress(false)
        }
    }


    fun checkOTP(pin: String, phoneNumber: String? = null) {
        if (pin.length != 5) {
            errorMessage.value = Event(ErrorModel(message = "Doğrulama kodunu kontrol ediniz."))
            return
        }
        if (phoneNumber != null) {
            // reset password
            verifyCode(pin)
        } else {
            // register
            verifyPhone(pin)
        }
    }

    private fun verifyPhone(pin: String) {
        toggleProgress(true)
        viewModelScope.launch {
            val checkResponse = userRepository.verifyPhone(code = pin, token = otpToken)
            if (checkResponse != null) {
                errorMessage.value = Event(ErrorModel(message = checkResponse.message))
            } else {
                navigate(OTPinFragmentDirections.toAccountCreatedFragment())
            }
            toggleProgress(false)
        }
    }

    private fun verifyCode(pin: String) {
        toggleProgress(true)
        viewModelScope.launch {
            val checkResponse = userRepository.verifyCode(code = pin, token = otpToken)
            if (checkResponse != null) {
                errorMessage.value = Event(ErrorModel(message = checkResponse.message))
            } else {
                navigate(OTPinFragmentDirections.toResetPasswordFragment())
            }
            toggleProgress(false)
        }
    }

    fun resendOtp() {
        requestOTP()
    }

    fun login(phone: String, password: String) {
        if (phone.isEmpty()) {
            errorMessage.value = Event(ErrorModel(message = "Lütfen telefon numarası giriniz"))
            return
        }
        if (password.isEmpty()) {
            errorMessage.value = Event(ErrorModel(message = "Lütfen Bir şifre giriniz"))
            return
        }
        toggleProgress(true)
        viewModelScope.launch {
            val loginResponse = userRepository.login(phone, password)
            if (loginResponse.error != null) {
                errorMessage.value = Event(ErrorModel(message = loginResponse.error.message))
                return@launch
            }
            if (loginResponse.user.isPhoneVerified.not()) {
                UserHelper.phoneNumber = loginResponse.user.phone
                navigate(LoginFragmentDirections.toOTPFragment())
                return@launch
            }
            UserHelper.user = loginResponse.user
            UserHelper.tokens = loginResponse.tokens
            navigate(NavigationCommand.ToActivity(MainActivity::class.java))
            toggleProgress(false)
        }
    }

    fun onResetPasswordPhoneNumber(phoneNumber: String) {
        if (phoneNumber.isValidPhone()) {
            if (phoneNumber.isEmpty()) {
                errorMessage.value = Event(ErrorModel(message = "Lütfen telefon numarası giriniz"))
                return
            }
            toggleProgress(true)
            viewModelScope.launch {
                val otpResponse = userRepository.forgotPassword(phoneNumber)
                if (otpResponse.error != null) {
                    errorMessage.value = Event(ErrorModel(message = otpResponse.error.message))
                } else {
                    otpToken = otpResponse.token
                    UserHelper.phoneNumber = phoneNumber
                    navigate(ResetPasswordFragmentDirections.toOTPFragment(phoneNumber = phoneNumber))
                }
                toggleProgress(false)
            }

        }
    }

    fun onSetNewPassword(p1: String, p2: String) {
        if (p1 != p2) {
            errorMessage.value = Event(ErrorModel(message = "Girilen şifreler eşleşmiyor"))
            return
        }
        toggleProgress(true)
        viewModelScope.launch {
            val newPasswordResponse = userRepository.resetPassword(p1, otpToken)
            if (newPasswordResponse != null) {
                errorMessage.value = Event(ErrorModel(message = newPasswordResponse.message))
            } else {
                navigate(NewPasswordFragmentDirections.toResetPasswordDescFragment())
            }
            toggleProgress(false)
        }
    }
}