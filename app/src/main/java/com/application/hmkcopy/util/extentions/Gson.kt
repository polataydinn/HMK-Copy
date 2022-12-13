package com.application.hmkcopy.util.extentions

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.io.Reader

fun <T> getObjectFromJson(json: String, cls: Class<T>): T {
    return Gson().fromJson(json, cls)
}

fun <T> getObjectFromJson(json: JsonElement, cls: Class<T>): T {
    return Gson().fromJson(json, cls)
}

fun <T> getObjectFromJson(json: Reader): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}
