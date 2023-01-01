package com.application.hmkcopy.repository.user

import com.application.hmkcopy.service.Service
import com.application.hmkcopy.service.request.*
import com.application.hmkcopy.service.response.ApiCallError
import com.application.hmkcopy.service.response.SendOTPResponse
import com.application.hmkcopy.service.response.UserResponse
import com.application.hmkcopy.util.extentions.castError
import com.application.hmkcopy.util.extentions.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val service: Service
) {
    suspend fun registerUser(
        phoneNumber: String,
        name: String,
        password: String
    ): UserResponse {
        val response = safeApiCall {
            service.registerUser(
                RegisterRequest(
                    phone = phoneNumber,
                    name = name,
                    password = password
                )
            )
        }
        return if (response.isSuccessful) {
            response.body() ?: UserResponse(
                error = ApiCallError(
                    "101",
                    "Kayıt oluşturulurken hata oluştu"
                )
            )
        } else {
            UserResponse(error = response.castError())
        }
    }

    suspend fun sendVerificationCodePhone(): SendOTPResponse {
        val response = safeApiCall { service.sendVerificationCodePhone() }
        return if (response.isSuccessful) {
            response.body() ?: SendOTPResponse(
                error = ApiCallError(
                    "101",
                    "Doğrulama kodu gönderilirken hata oluştu"
                )
            )
        } else {
            SendOTPResponse(error = response.castError())
        }
    }

    suspend fun verifyPhone(code: String, token: String): ApiCallError? {
        val response = safeApiCall { service.verifyPhone(
            VerifyPhoneRequest(
                code = code,
                token = token
            )
        ) }
        return if (response.isSuccessful) {
            null
        } else {
            response.castError<Unit, ApiCallError>()
        }
    }

    suspend fun verifyCode(code: String, token: String): ApiCallError? {
        val response = safeApiCall {
            service.verifyCode(
                VerifyPhoneRequest(
                    code = code,
                    token = token
                )
            )
        }
        return if (response.isSuccessful) {
            null
        } else {
            response.castError<Unit, ApiCallError>()
        }
    }

    suspend fun login(phoneNumber: String, password: String): UserResponse {
        val response = safeApiCall {
            service.login(
                LoginRequest(
                    phone = phoneNumber,
                    password = password
                )
            )
        }
        return if (response.isSuccessful) {
            response.body() ?: UserResponse(
                error = ApiCallError(
                    "101",
                    "Giriş yapılırken hata oluştu"
                )
            )
        } else {
            UserResponse(error = response.castError())
        }
    }

    suspend fun forgotPassword(phoneNumber: String): SendOTPResponse {
        val response = safeApiCall { service.forgotPassword(ForgotPasswordRequest(phoneNumber)) }
        return if (response.isSuccessful) {
            response.body() ?: SendOTPResponse(
                error = ApiCallError(
                    "101",
                    "Doğrulama kodu gönderilirken hata oluştu"
                )
            )
        } else {
            SendOTPResponse(error = response.castError())
        }
    }

    suspend fun resetPassword(password: String, token: String): ApiCallError? {
        val response = safeApiCall {
            service.resetPassword(
                ResetPasswordRequest(
                    password = password,
                    token = token
                )
            )
        }
        return if (response.isSuccessful) {
            null
        } else {
            response.castError<Unit, ApiCallError>()
        }
    }

}