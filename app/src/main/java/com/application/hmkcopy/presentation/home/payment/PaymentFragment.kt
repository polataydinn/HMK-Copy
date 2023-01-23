package com.application.hmkcopy.presentation.home.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentPaymentBinding
import com.application.hmkcopy.presentation.home.CommonViewModel

class PaymentFragment : BaseFragment<FragmentPaymentBinding, CommonViewModel>() {
    override val viewModel: CommonViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentBinding {
        return FragmentPaymentBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun updateUI() {
        super.updateUI()
        binding.apply {
            submitPayment.setOnClickListener {

            }
        }
    }
}