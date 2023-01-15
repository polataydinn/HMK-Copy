package com.application.hmkcopy.util.extentions

import android.util.Log
import com.application.hmkcopy.service.response.ApiCallError
import com.google.gson.Gson
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.io.Reader

inline fun <T, reified R> Response<T>.castError(): R? {
    return try {
        errorBody()?.charStream()?.castTo<R>()
    } catch (e: Exception) {
        null
    }
}

inline fun <reified T> Reader?.castTo(): T? {
    return this?.use {
        Gson().fromJson(it, T::class.java)
    }
}


suspend fun <T> safeApiCall(
    apiCall: suspend () -> Response<T>
): Response<T> {
    return try {
        apiCall.invoke()
    } catch (e: Exception) {

        val (code, reason) = when (e) {
            is IOException -> {
                101 to "Internet bağlantısını kontrol ediniz."
            }
            is HttpException -> {
                val code = e.code()
                code to "Network Error. code: $code"
            }
            else -> {
                500 to "Sunucuya bağlanırken hata oluştu."
            }
        }
        Response.error(
            404, Gson().toJson(ApiCallError(code.toString(), reason)).toResponseBody()
        )
    }
}