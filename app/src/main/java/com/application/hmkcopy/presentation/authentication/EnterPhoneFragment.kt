package com.application.hmkcopy.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.navGraphViewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentEnterPhoneBinding
import com.application.hmkcopy.repository.user.UserHelper
import com.application.hmkcopy.util.extentions.isValidPhone

class EnterPhoneFragment : BaseFragment<FragmentEnterPhoneBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEnterPhoneBinding {
        return FragmentEnterPhoneBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun updateUI() {
        super.updateUI()
        binding.registerPhoneNumberInput.setText(UserHelper.phoneNumber)
    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            registerWithPhoneButton.setOnClickListener {
                viewModel.onRegisterPhoneNumber(registerPhoneNumberInput.text.toString())
            }
            registerPhoneNumberInput.setOnEditorActionListener { _, actionId, _ ->
                if(actionId == EditorInfo.IME_ACTION_NEXT) {
                    viewModel.onRegisterPhoneNumber(registerPhoneNumberInput.text.toString())
                    true
                } else false
            }
        }
    }
}