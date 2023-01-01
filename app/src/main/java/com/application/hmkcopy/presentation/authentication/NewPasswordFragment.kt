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
import com.application.hmkcopy.databinding.FragmentNewPasswordBinding
import com.application.hmkcopy.navigation.NavigationCommand

class NewPasswordFragment : BaseFragment<FragmentNewPasswordBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewPasswordBinding {
        return FragmentNewPasswordBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            newPasswordResetButton.setOnClickListener {
                val p1 = createNewPasswordInput.text?.toString() ?: ""
                val p2 = createNewPasswordRepeatInput.text?.toString() ?: ""
                viewModel.onSetNewPassword(p1, p2)
            }
            newPasswordBackButton.setOnClickListener {
                viewModel.navigate(NavigationCommand.Back)
            }
        }
    }
}