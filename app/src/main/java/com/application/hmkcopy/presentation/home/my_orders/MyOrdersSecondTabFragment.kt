package com.application.hmkcopy.presentation.home.my_orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentMyOrdersSecondTabBinding
import com.application.hmkcopy.presentation.home.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersSecondTabFragment :
    BaseFragment<FragmentMyOrdersSecondTabBinding, CommonViewModel>() {
    override val viewModel: CommonViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyOrdersSecondTabBinding {
        return FragmentMyOrdersSecondTabBinding.inflate(inflater, container, false)
    }

}