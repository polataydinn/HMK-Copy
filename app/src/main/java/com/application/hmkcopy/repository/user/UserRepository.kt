package com.application.hmkcopy.repository.user

import com.application.hmkcopy.service.Service
import com.application.hmkcopy.service.request.LoginRequest
import com.application.hmkcopy.service.request.RegisterRequest
import com.application.hmkcopy.service.request.VerifyPhoneRequest
import com.application.hmkcopy.service.response.ApiCallError
import com.application.hmkcopy.service.response.SendOTPResponse
import com.application.hmkcopy.service.response.UserResponse
import com.application.hmkcopy.util.extentions.castError
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
        val response = service.registerUser(
            RegisterRequest(
                phone = phoneNumber,
                name = name,
                password = password
            )
        )
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

    suspend fun requestOTP(): SendOTPResponse {
        val response = service.sendVerificationCode()
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

    suspend fun checkOTP(code: String, token: String): ApiCallError? {
        val response = service.verifyPhone(
            VerifyPhoneRequest(
                code = code,
                token = token
            )
        )
        return if (response.isSuccessful) {
            null
        } else {
            response.castError<Unit, ApiCallError>()
        }
    }

    suspend fun login(phoneNumber: String, password: String): UserResponse {
        val response = service.login(
            LoginRequest(
                phone = phoneNumber,
                password = password
            )
        )
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
}