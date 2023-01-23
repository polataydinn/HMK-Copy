package com.application.hmkcopy.presentation.home.order_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentOrderMoreDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderMoreDetailFragment : BaseFragment<FragmentOrderMoreDetailBinding, OrderViewModel>() {
    override val viewModel: OrderViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderMoreDetailBinding {
        return FragmentOrderMoreDetailBinding.inflate(inflater, container, false)
    }
}