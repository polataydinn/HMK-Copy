package com.application.hmkcopy.service

import com.application.hmkcopy.service.request.*
import com.application.hmkcopy.service.response.UserResponse
import com.application.hmkcopy.service.response.SendOTPResponse
import com.application.hmkcopy.service.response.Tokens
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Service {

    // Auth

    @POST("auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<UserResponse>

    @POST("auth/send-verification-phone")
    suspend fun sendVerificationCodePhone(): Response<SendOTPResponse>

    @POST("auth/verify-phone")
    suspend fun verifyPhone(@Body verifyPhoneRequest: VerifyPhoneRequest): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<UserResponse>

    @POST("auth/register")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<Tokens>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<SendOTPResponse>

    @POST("auth/verify-code")
    suspend fun verifyCode(@Body verifyPhoneRequest: VerifyPhoneRequest): Response<Unit>

    @POST("auth/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<Unit>

}