package com.application.hmkcopy.presentation.home.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentPaymentErrorBinding
import com.application.hmkcopy.presentation.home.copy_center.CopyCenterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentErrorFragment : BaseFragment<FragmentPaymentErrorBinding, CopyCenterViewModel>() {
    override val viewModel: CopyCenterViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentErrorBinding {
        return FragmentPaymentErrorBinding.inflate(inflater, container, false)
    }
}