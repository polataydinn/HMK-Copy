package com.application.hmkcopy.presentation.home.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentOrderDetailBinding
import com.application.hmkcopy.presentation.home.CommonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding, OrderViewModel>() {
    override val viewModel: OrderViewModel by viewModels()

    private val adapter: OrderDetailAdapter by lazy { OrderDetailAdapter() }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderDetailBinding {
        return FragmentOrderDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.orderDetailRecyclerView.adapter = adapter
        viewModel.getOrderedItems()
        setBottomButtonListener()
        adapterListener()
    }

    private fun adapterListener() {
        adapter.onItemClick = {
            val status = it.status
            val seller = it.seller
            val tempItem = it
            tempItem.products?.forEach { item ->
                item?.seller = seller
                item?.status = status
            }
            viewModel.navigate(OrderDetailFragmentDirections.actionOrderDetailFragmentToOrderMoreDetailFragment(tempItem))
        }
    }

    private fun setBottomButtonListener() {
        mainActivity()?.setDocumentButtonListener {
            if (navController.currentDestination?.label == "OrderDetailFragment") {
                viewModel.navigateBack()
            }
        }
    }

    override fun configureObservers() {
        super.configureObservers()
        viewModel.orders.observe(viewLifecycleOwner) {
            adapter.submitList(it.orders)
        }
    }

}