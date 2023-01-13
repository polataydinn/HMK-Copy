package com.application.hmkcopy.service

import com.application.hmkcopy.service.request.*
import com.application.hmkcopy.service.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

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

    @POST("auth/refresh-tokens")
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Response<Tokens>

    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<SendOTPResponse>

    @POST("auth/verify-code")
    suspend fun verifyCode(@Body verifyPhoneRequest: VerifyPhoneRequest): Response<Unit>

    @POST("auth/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<Unit>

    @POST("user/document/upload")
    suspend fun documentUpload(@Body documentRequest: UploadDocumentRequest): Response<UploadDocumentResponse>

    @POST("user/document")
    suspend fun uploadDocumentVerify(@Body uploadVerifyRequest: UploadVerifyRequest): Response<UploadVerifyResponse>

    @GET("user/document")
    suspend fun getDocuments(): Response<DocumentsResponse>

    @DELETE("user/document/{documentId}")
    suspend fun deleteDocument(@Path("documentId") documentId: String): Response<Unit>

    @GET("seller")
    suspend fun getAllSellers(): Response<SellersResponse>

    @POST("checkout")
    suspend fun createCheckoutBasket(@Body createCheckoutBasketRequest: CreateCheckoutBasketRequest): Response<CreateCheckoutBasketResponse>

    @PATCH("checkout/seller")
    suspend fun setSellerToCheckout(@Body sellerPatchRequest: SellerPatchRequest): Response<SellerPatchResponse>

    @GET("checkout/all")
    suspend fun checkout(): Response<CheckoutResponse>

    @PATCH("checkout/{productId}")
    suspend fun updateProduct(@Path("productId") productId: String, @Body updateBasketRequest: UpdateBasketRequest): Response<Unit>

}