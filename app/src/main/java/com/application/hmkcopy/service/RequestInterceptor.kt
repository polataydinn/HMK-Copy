package com.application.hmkcopy.service

import android.util.Log
import com.application.hmkcopy.repository.user.UserHelper
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer ${UserHelper.tokens?.access?.token ?: ""}")
        Log.d("TAG", "intercept: $request")
        Log.d("TAG", "Bearer ${UserHelper.tokens?.access?.token ?: ""}")
        return chain.proceed(request.build())
    }
}