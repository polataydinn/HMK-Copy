package com.application.hmkcopy.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentAccountCreatedBinding
import com.application.hmkcopy.navigation.NavigationCommand
import com.application.hmkcopy.presentation.home.MainActivity

class AccountCreatedFragment :
    BaseFragment<FragmentAccountCreatedBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountCreatedBinding {
        return FragmentAccountCreatedBinding.inflate(inflater, container, false)
    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            accountCreatedCreateOrderButton.setOnClickListener {
                viewModel.navigate(AccountCreatedFragmentDirections.actionAccountCreatedFragmentToLoginFragment())
            }
        }
    }

}