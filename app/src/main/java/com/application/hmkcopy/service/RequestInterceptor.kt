package com.application.hmkcopy.service

import aws.smithy.kotlin.runtime.http.HttpStatusCode
import com.application.hmkcopy.repository.user.UserHelper
import com.application.hmkcopy.service.request.RefreshTokenRequest
import dagger.Provides
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class RequestInterceptor @Inject constructor(
    private var service: Provider<Service>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer ${UserHelper.tokens?.access?.token ?: ""}")
        val result = chain.proceed(request.build())
        if (result.code == HttpStatusCode.Unauthorized.value) {
            result.close()
            refreshToken(UserHelper.tokens?.refresh?.token ?: "")
            val secondRequest = chain.request().newBuilder()
            secondRequest.addHeader(
                "Authorization", "Bearer ${UserHelper.tokens?.access?.token ?: ""}"
            )
            chain.proceed(secondRequest.build())
        } else {
            result
        }
    }

    private suspend fun refreshToken(refreshToken: String) {
        val result = service.get().refreshToken(
            RefreshTokenRequest(
                refreshToken = refreshToken
            )
        )
        if (result.isSuccessful) {
            UserHelper.tokens = result.body()
        }
    }
}