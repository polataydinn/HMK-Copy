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
import com.application.hmkcopy.databinding.FragmentRegisterNamePassBinding

class RegisterNamePassFragment :
    BaseFragment<FragmentRegisterNamePassBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterNamePassBinding {
        return FragmentRegisterNamePassBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            registerPasswordInput.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val nameAndSurname = registerNameSurnameInput.text?.toString() ?: ""
                    val password = registerPasswordInput.text?.toString() ?: ""
                    viewModel.register(nameAndSurname, password)
                    true
                } else false
            }
            registerNameSurnameContinueButton.setOnClickListener {
                val nameAndSurname = registerNameSurnameInput.text?.toString() ?: ""
                val password = registerPasswordInput.text?.toString() ?: ""
                viewModel.register(nameAndSurname, password)
            }
        }
    }
}