package com.application.hmkcopy.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PasswordFragment : BaseFragment<FragmentPasswordBinding, ProfileViewModel>() {
    override val viewModel: ProfileViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPasswordBinding {
        return FragmentPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordUpdateButton.setOnClickListener {
            val oldPass = binding.passwordCurrentPasswordEdittext.text.toString()
            val newPass = binding.passNewPassEdittext.text.toString()
            val newPassRepeat = binding.passNewPassRepeatEdittext.text.toString()
            viewModel.resetPassword(oldPass, newPass, newPassRepeat)
        }
    }

    override fun updateUI() {
        super.updateUI()
        profileActivity()?.setTitleVisibility(true)
        profileActivity()?.setTitleText("Şifre Ayarları")
        profileActivity()?.setBackButtonListener {
            if (navController.currentDestination?.label == "fragment_password"){
                viewModel.navigateBack()
            }
        }
    }
}