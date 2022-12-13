package com.application.hmkcopy.repository.user

import com.application.hmkcopy.service.Service
import com.application.hmkcopy.service.request.RegisterRequest
import com.application.hmkcopy.service.request.VerifyPhoneRequest
import com.application.hmkcopy.service.response.ApiCallError
import com.application.hmkcopy.service.response.RegisterResponse
import com.application.hmkcopy.service.response.SendOTPResponse
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
    ): RegisterResponse? = try{
        val response = service.registerUser(
            RegisterRequest(
                phone = phoneNumber,
                name = name,
                password = password
            )
        )
         if (response.isSuccessful) {
            response.body()
        } else {
            RegisterResponse(error = response.castError())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    suspend fun requestOTP(): SendOTPResponse? {
        val response = service.sendVerificationCode()
        return if (response.isSuccessful) {
            response.body()
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
}