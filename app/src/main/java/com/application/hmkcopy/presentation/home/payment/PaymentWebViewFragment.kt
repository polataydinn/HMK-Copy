package com.application.hmkcopy.presentation.home.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentPaymentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentWebViewFragment : BaseFragment<FragmentPaymentWebViewBinding, PaymentViewModel>() {
    override val viewModel: PaymentViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentWebViewBinding {
        return FragmentPaymentWebViewBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getWebView()
        mainActivity()?.makeBottomNavigationInvisible()
        mainActivity()?.setBackButtonListeners {
            if (navController.currentDestination?.label == "fragment_payment_web_view"){
                viewModel.popBackToMain()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    override fun configureObservers() {
        super.configureObservers()
        viewModel.webVew.observe(viewLifecycleOwner){
            binding.paymentWebView.apply {
                loadDataWithBaseURL("", it,"text/html", "UTF-8", "")
                settings.javaScriptEnabled = true
                settings.lightTouchEnabled = true
                isVerticalScrollBarEnabled = true
                settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
                requestFocus(View.FOCUS_DOWN)
                requestFocusFromTouch()
                setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> if (!v.hasFocus()) {
                            v.requestFocus()
                        }
                    }
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity()?.makeBottomNavigationVisible()
    }
}