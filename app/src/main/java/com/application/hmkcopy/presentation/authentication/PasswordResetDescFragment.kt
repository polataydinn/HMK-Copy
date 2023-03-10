package com.application.hmkcopy.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentPasswordResetDescBinding

class PasswordResetDescFragment :
    BaseFragment<FragmentPasswordResetDescBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPasswordResetDescBinding {
        return FragmentPasswordResetDescBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.passwordResetDescBackButton.setOnClickListener {
            viewModel.navigateBack()
        }
        binding.passwordResetDescContinueButton.setOnClickListener {
            viewModel.navigate(PasswordResetDescFragmentDirections.actionPasswordResetDescFragmentToAuthenticationFragment())
        }
    }

}