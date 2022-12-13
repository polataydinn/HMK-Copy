package com.application.hmkcopy.util.extentions

import com.google.gson.Gson
import retrofit2.Response
import java.io.Reader

inline fun <T, reified R> Response<T>.castError(): R? {
    return errorBody()?.charStream()?.castTo<R>()
}

inline fun <reified T> Reader?.castTo(): T? {
    return this?.use {
        Gson().fromJson(it, T::class.java)
    }
}