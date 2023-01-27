package com.application.hmkcopy.presentation.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.application.hmkcopy.R
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.repository.user.UserHelper
import com.application.hmkcopy.service.response.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        if (UserHelper.user.isValid() && (UserHelper.tokens != null)) {
            val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun User?.isValid(): Boolean {
        return this != null && isPhoneVerified
    }
}