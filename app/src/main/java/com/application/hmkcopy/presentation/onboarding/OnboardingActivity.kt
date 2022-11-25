package com.application.hmkcopy.presentation.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.application.hmkcopy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }
}