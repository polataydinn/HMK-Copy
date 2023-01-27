package com.application.hmkcopy.repository.user

import com.application.hmkcopy.service.response.Tokens
import com.application.hmkcopy.service.response.User
import com.application.hmkcopy.util.SharedPreferences
import com.application.hmkcopy.util.extentions.getObjectFromJson
import com.google.gson.Gson

object UserHelper {

    object Key {
        const val authorization = "UserHelper.Key.authorization"
        const val email = "UserHelper.Key.email"
        const val name = "UserHelper.Key.name"
        const val phoneNumber = "UserHelper.Key.phoneNumber"
        const val tokens = "UserHelper.Key.tokens"
        const val user = "UserHelper.Key.user"
        const val darkModeStatus = "UserHelper.Key.darkModeStatus"
        const val skipped = "UserHelper.Key.skipped"
        const val cachedMail = "UserHelper.Key.cachedMail"
        const val phoneNumberExtension = "@dropkick.com"
        const val onboardingStep = "UserHelper.Key.OnboardingStep"
        const val preferencesStep = "UserHelper.Key.preferencesStep"
        const val notificationPreferences = "UserHelper.Key.notificationPreferences"
        const val otpNumber = "UserHelper.Key.otpNumber"
        const val otpToken = "UserHelper.Key.otpToken"
    }

    var user: User?
        get() {
            return try {
                getObjectFromJson(
                    SharedPreferences.getString(Key.user),
                    User::class.java
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        set(value) {
            SharedPreferences.put(Key.user, Gson().toJson(value ?: ""))
        }

    var isOnBoardingShowed: Boolean?
        get() {
            return try {
                getObjectFromJson(
                    SharedPreferences.getString(Key.onboardingStep),
                    Boolean::class.java
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        set(value) {
            SharedPreferences.put(Key.onboardingStep, Gson().toJson(value ?: false))
        }

    val id: String?
        get() {
            return user?.id
        }

    var phoneNumber: String
        get() {
            return SharedPreferences.getString(Key.phoneNumber)
        }
        set(value) {
            SharedPreferences.put(Key.phoneNumber, value)
        }

    var bearerAuthorization: String
        get() {
            val token = SharedPreferences.getString(Key.authorization)
            return if (token.isEmpty()) "" else "Bearer $token"
        }
        set(value) {
            SharedPreferences.put(Key.authorization, value.replace("Bearer ", ""))
        }

    var tokens: Tokens?
        get() {
            return try {
                getObjectFromJson(
                    SharedPreferences.getString(Key.tokens),
                    Tokens::class.java
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        set(value) {
            SharedPreferences.put(Key.tokens, Gson().toJson(value ?: ""))
        }

    var userEmail: String
        get() {
            val userEmail = SharedPreferences.getString(Key.email)
            return userEmail.ifEmpty { "" }
        }
        set(value) {
            SharedPreferences.put(Key.email, value)
        }

    var userName: String
        get() {
            val userName = SharedPreferences.getString(Key.name)
            return userName.ifEmpty { "" }
        }
        set(value) {
            SharedPreferences.put(Key.name, value)
        }

}