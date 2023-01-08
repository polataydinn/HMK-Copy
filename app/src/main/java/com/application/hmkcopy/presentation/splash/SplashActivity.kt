package com.application.hmkcopy.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.application.hmkcopy.R
import com.application.hmkcopy.presentation.authentication.AuthenticationActivity
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.presentation.onboarding.OnboardingActivity
import com.application.hmkcopy.repository.user.UserHelper
import com.application.hmkcopy.service.response.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val timer = object: CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val nextActivity = if (UserHelper.user.isValid() && (UserHelper.tokens != null)) {
                    MainActivity::class.java
                } else if (UserHelper.isOnBoardingShowed == null || UserHelper.isOnBoardingShowed == false){
                    OnboardingActivity::class.java
                }else{
                    AuthenticationActivity::class.java
                }
                val intent = Intent(this@SplashActivity, nextActivity)
                startActivity(intent)
                finish()
            }
        }
        timer.start()
    }

    private fun User?.isValid(): Boolean {
        return this != null && isPhoneVerified
    }
}