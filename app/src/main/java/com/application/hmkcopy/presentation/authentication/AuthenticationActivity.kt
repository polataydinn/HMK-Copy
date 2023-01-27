package com.application.hmkcopy.presentation.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.application.hmkcopy.R
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.presentation.onboarding.OnboardingActivity
import com.application.hmkcopy.repository.user.UserHelper
import com.application.hmkcopy.service.response.User
import com.yagmurerdogan.toasticlib.Toastic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
    }

    fun errorToast(text: String) {
        Toastic.toastic(
            context = this,
            message = text,
            duration = Toastic.LENGTH_SHORT,
            type = Toastic.ERROR,
            isIconAnimated = true
        ).show()
    }

    fun successToast(text: String) {
        Toastic.toastic(
            context = this,
            message = text,
            duration = Toastic.LENGTH_SHORT,
            type = Toastic.SUCCESS,
            isIconAnimated = true
        ).show()
    }

    fun warningToast(text: String) {
        Toastic.toastic(
            context = this,
            message = text,
            duration = Toastic.LENGTH_SHORT,
            type = Toastic.WARNING,
            isIconAnimated = true
        ).show()
    }
}