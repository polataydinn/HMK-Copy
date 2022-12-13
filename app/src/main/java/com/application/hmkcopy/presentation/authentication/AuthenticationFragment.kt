package com.application.hmkcopy.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentAuthenticationBinding
import com.application.hmkcopy.repository.user.UserHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationFragment : BaseFragment<FragmentAuthenticationBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAuthenticationBinding {
        return FragmentAuthenticationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            authenticationRegisterWithPhoneNumberButton.setOnClickListener {
                viewModel.navigate(AuthenticationFragmentDirections.toEnterPhoneFragment())
            }
            authenticationLoginButton.setOnClickListener {
                viewModel.navigate(AuthenticationFragmentDirections.toLoginFragment())
            }
        }
    }
}