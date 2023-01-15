package com.application.hmkcopy.presentation.home.copy_center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentConfirmOrderBinding
import com.application.hmkcopy.presentation.home.copy_center.adapter.OrderConfirmAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmOrderFragment :
    BaseFragment<FragmentConfirmOrderBinding, PrintConfigurationViewModel>() {
    override val viewModel: PrintConfigurationViewModel by viewModels()

    private val adapter: OrderConfirmAdapter by lazy { OrderConfirmAdapter() }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmOrderBinding {
        return FragmentConfirmOrderBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.orderConfirmRecyclerView.adapter = adapter
        viewModel.checkout()
    }

    override fun configureObservers() {
        super.configureObservers()
        viewModel.items.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
                binding.orderConfirmCardView.isVisible = true
            }
        }
    }
}