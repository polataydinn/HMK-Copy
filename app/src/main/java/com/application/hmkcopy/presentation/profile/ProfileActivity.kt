package com.application.hmkcopy.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.application.hmkcopy.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun setSettingsButtonListener(onItemClick: () -> Unit){
        binding.profileActivitySettingsButton.setOnClickListener {
            onItemClick.invoke()
        }
    }
    fun setBackButtonListener(onItemClick: () -> Unit){
        binding.profileActivityBackButton.setOnClickListener {
            onItemClick.invoke()
        }
    }

    fun setTitleVisibility(isVisible: Boolean){
        binding.profileActivitySettingsButton.isVisible = !isVisible
        binding.profileMainTitle.isVisible = isVisible
    }

    fun setTitleText(title: String){
        binding.profileMainTitle.text = title
    }
}