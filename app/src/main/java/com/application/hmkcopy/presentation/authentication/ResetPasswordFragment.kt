package com.application.hmkcopy.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentResetPasswordBinding
import com.application.hmkcopy.navigation.NavigationCommand
import com.application.hmkcopy.presentation.home.copy_center.CopyCenterMapFragmentArgs
import com.application.hmkcopy.util.extentions.isValidPhone

class ResetPasswordFragment :
    BaseFragment<FragmentResetPasswordBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentResetPasswordBinding {
        return FragmentResetPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            resetPasswordBackButton.setOnClickListener {
                viewModel.navigate(NavigationCommand.Back)
            }
            resetPasswordNextButton.setOnClickListener {
                viewModel.onResetPasswordPhoneNumber(resetPasswordPhoneInput.text?.toString() ?: "")
            }
        }
    }
}