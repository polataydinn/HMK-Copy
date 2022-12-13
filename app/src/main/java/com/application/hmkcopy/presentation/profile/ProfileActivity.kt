package com.application.hmkcopy.presentation.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.application.hmkcopy.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}