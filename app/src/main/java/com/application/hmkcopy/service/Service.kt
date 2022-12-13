package com.application.hmkcopy.service

import com.application.hmkcopy.service.request.RegisterRequest
import com.application.hmkcopy.service.request.VerifyPhoneRequest
import com.application.hmkcopy.service.response.RegisterResponse
import com.application.hmkcopy.service.response.SendOTPResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {

    // Auth

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("auth/send-verification-phone")
    suspend fun sendVerificationCode(): Response<SendOTPResponse>

    @POST("auth/verify-phone")
    suspend fun verifyPhone(@Body verifyPhoneRequest: VerifyPhoneRequest): Response<Unit>
}