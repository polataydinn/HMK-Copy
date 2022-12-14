package com.application.hmkcopy.service

import com.application.hmkcopy.service.request.LoginRequest
import com.application.hmkcopy.service.request.RefreshTokenRequest
import com.application.hmkcopy.service.request.RegisterRequest
import com.application.hmkcopy.service.request.VerifyPhoneRequest
import com.application.hmkcopy.service.response.UserResponse
import com.application.hmkcopy.service.response.SendOTPResponse
import com.application.hmkcopy.service.response.Tokens
import com.application.hmkcopy.service.response.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    // Auth

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<UserResponse>

    @POST("auth/send-verification-phone")
    suspend fun sendVerificationCode(): Response<SendOTPResponse>

    @POST("auth/verify-phone")
    suspend fun verifyPhone(@Body verifyPhoneRequest: VerifyPhoneRequest): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<UserResponse>

    @POST("auth/register")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<Tokens>

}