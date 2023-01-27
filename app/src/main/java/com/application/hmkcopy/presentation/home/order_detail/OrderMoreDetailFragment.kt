package com.application.hmkcopy.presentation.home.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentOrderMoreDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderMoreDetailFragment : BaseFragment<FragmentOrderMoreDetailBinding, OrderViewModel>() {
    override val viewModel: OrderViewModel by viewModels()
    private val adapter: OrderMoreAdapter by lazy { OrderMoreAdapter() }

    private val args : OrderMoreDetailFragmentArgs by navArgs()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderMoreDetailBinding {
        return FragmentOrderMoreDetailBinding.inflate(inflater, container, false)
    }

    override fun configureObservers() {
        super.configureObservers()
        viewModel.orders.observe(viewLifecycleOwner){
            val item = it.orders?.filter { mIt -> mIt?.id == args.orderResponse.id }?.get(0)
            val seller = item?.seller
            val status = item?.status

            item?.products?.forEach { mItem ->
                mItem?.seller = seller
                mItem?.status = status
            }

            adapter.submitList(item?.products)
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity()?.makeBottomButtonsInvisible()
        mainActivity()?.changeIconToRefresh()
        mainActivity()?.setPageTitle("Sipariş Detayları")
        mainActivity()?.setBackButtonVisible()
        mainActivity()?.setFabButtonClickListener {
            if (navController.currentDestination?.label == "fragment_order_more_detail"){
                viewModel.getOrderedItems()
            }
        }
        mainActivity()?.setBackButtonListeners {
            if (navController.currentDestination?.label == "fragment_order_more_detail"){
                viewModel.navigateBack()
            }
        }
    }

    override fun updateUI() {
        super.updateUI()
        binding.productListRecyclerView.adapter = adapter
        adapter.submitList(args.orderResponse.products)
        binding.dotsIndicator.attachTo(binding.productListRecyclerView)
    }

    override fun onPause() {
        super.onPause()
        mainActivity()?.makeBottomButtonsVisible()
        mainActivity()?.changeMainIconToPlus()
        mainActivity()?.setPageTitleInvisible()
        mainActivity()?.setAvatarVisible()
    }
}