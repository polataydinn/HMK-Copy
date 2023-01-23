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
import com.application.hmkcopy.databinding.FragmentLoginBinding
import com.application.hmkcopy.repository.user.UserHelper

class LoginFragment : BaseFragment<FragmentLoginBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun updateUI() {
        super.updateUI()
        binding.loginPhoneNumber.setText(UserHelper.phoneNumber)
    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            loginButton.setOnClickListener {
                val phone = binding.loginPhoneNumber.text?.toString() ?: ""
                val password = binding.loginPasswordInput.text?.toString() ?: ""
                viewModel.login(phone, password)
            }
            loginFragmentForgetPasswordButton.setOnClickListener {
                viewModel.navigate(LoginFragmentDirections.toResetPasswordFragment())
            }
        }
    }

}