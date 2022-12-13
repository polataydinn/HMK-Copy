package com.application.hmkcopy.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentOtpPinBinding

class OTPinFragment : BaseFragment<FragmentOtpPinBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by hiltNavGraphViewModels(R.id.nav_authentication)

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOtpPinBinding {
        return FragmentOtpPinBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.requestOTP()
    }

    override fun configureCallbacks() {
        super.configureCallbacks()
        binding.apply {
            otpConfirmButton.setOnClickListener {
                viewModel.checkOtp(otpReceivedSmsInput.text?.toString() ?: "")
            }
            otpBackButton.setOnClickListener {
                activity?.onBackPressed()
            }
            resendButton.setOnClickListener {
                viewModel.resendOtp()
            }
        }
    }

}