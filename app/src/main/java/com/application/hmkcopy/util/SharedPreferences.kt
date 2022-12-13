package com.application.hmkcopy.util

import android.content.Context
import android.content.SharedPreferences
import com.application.hmkcopy.BuildConfig
import com.application.hmkcopy.app.MyApplication

object SharedPreferences {

    object Key {
        const val name = "${BuildConfig.APPLICATION_ID}.storage"
    }

    private val preferences: SharedPreferences? = MyApplication.instance.getSharedPreferences(Key.name, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor? = preferences?.edit()

    fun put(key: String, value: String?) {
        editor?.putString(key, value ?: "")?.apply()
    }

    fun put(key: String, value: Boolean?) {
        editor?.putBoolean(key, value ?: false)?.apply()
    }

    fun put(key: String, value: Int?) {
        editor?.putInt(key, value ?: -1)?.apply()
    }

    fun remove(key: String) {
        editor?.remove(key)?.apply()
    }

    fun getString(key: String): String {
        return preferences?.getString(key, "") ?: ""
    }

    fun getInt(key: String, default: Int? = null): Int {
        return preferences?.getInt(key, 0) ?: (default ?: 0)
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean {
        return preferences?.getBoolean(key, defValue) ?: defValue
    }

}