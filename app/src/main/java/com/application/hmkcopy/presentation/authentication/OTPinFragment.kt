package com.application.hmkcopy.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentOtpPinBinding

class OTPinFragment : BaseFragment<FragmentOtpPinBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOtpPinBinding {
        return FragmentOtpPinBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}