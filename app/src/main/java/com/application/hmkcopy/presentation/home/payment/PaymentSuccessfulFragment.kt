package com.application.hmkcopy.presentation.home.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentPaymentSuccessfulBinding
import com.application.hmkcopy.presentation.home.copy_center.CopyCenterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentSuccessfulFragment :
    BaseFragment<FragmentPaymentSuccessfulBinding, CopyCenterViewModel>() {
    override val viewModel: CopyCenterViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentSuccessfulBinding {
        return FragmentPaymentSuccessfulBinding.inflate(inflater, container, false)
    }

    override fun updateUI() {
        super.updateUI()
        mainActivity()?.apply {
            makeBottomNavigationInvisible()
            setPageTitle("Sipariş Tamamlandı")
            setBackButtonVisible()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mainActivity()?.makeBottomNavigationVisible()
    }
}