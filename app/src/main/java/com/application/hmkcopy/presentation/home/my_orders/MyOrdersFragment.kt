package com.application.hmkcopy.presentation.home.my_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.data.model.DocumentTransfer
import com.application.hmkcopy.databinding.FragmentMyOrdersBinding
import com.application.hmkcopy.presentation.home.CommonViewModel
import com.application.hmkcopy.presentation.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrdersFragment : BaseFragment<FragmentMyOrdersBinding, CommonViewModel>() {

    override val viewModel: CommonViewModel by viewModels()
    private val orderListAdapter: OrderListAdapter by lazy { OrderListAdapter() }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyOrdersBinding {
        return FragmentMyOrdersBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDocuments()
        setNavButtonListeners()
        setHeaders()
        setRecyclerView()
        setObservers()
        setItemListener()
        setButtonListeners()
        checkDeepLink()
        mainActivity()?.changeMainIconToArrow()
        mainActivity()?.shouldRefreshDocuments { viewModel.getDocuments() }
    }

    private fun setNavButtonListeners() {
        mainActivity()?.setOrderDetailButtonListener {
            if (navController.currentDestination?.label == "fragment_my_orders") {
                viewModel.navigate(MyOrdersFragmentDirections.actionMyOrdersFragmentToOrderDetailFragment())
            }
        }
    }

    private fun checkDeepLink() {
        val path = mainActivity()?.currentPath
        if (!path.isNullOrEmpty() && path.contains("/payment_successful_for_mobil")) {
            findNavController().navigate(MyOrdersFragmentDirections.actionMyOrdersFragmentToPaymentSuccessfulFragment())
            mainActivity()?.currentPath = ""
        } else if (!path.isNullOrEmpty() && path.contains("payment_error_for_mobil")) {
            findNavController().navigate(MyOrdersFragmentDirections.actionMyOrdersFragmentToPaymentErrorFragment())
            mainActivity()?.currentPath = ""
        }
    }

    private fun setHeaders() {
        (activity as MainActivity).apply {
            setAvatarVisible()
            setPageDescInvisible()
            setPageTitleInvisible()
        }
    }

    private fun setButtonListeners() {
        mainActivity()?.setFabButtonClickListener {
            if (viewModel.isAnyItemSelected.value == true && navController.currentDestination?.label == "fragment_my_orders") {

                val documents =
                    DocumentTransfer(listOfDocuments = viewModel.documents.value?.filter { it.isItemSelected })
                findNavController().navigate(
                    MyOrdersFragmentDirections.actionMyOrdersFragmentToCopyCenterChooserFragment(
                        documents
                    )
                )
            }
        }
    }

    private fun setObservers() {
        viewModel.documents.observe(viewLifecycleOwner) {
            if (viewModel.isGetDocument.value == true) {
                orderListAdapter.clear()
                viewModel.isGetDocument.value = false
            }
            binding.noItemFoundContainer.isVisible = it.isNullOrEmpty()
            binding.myOrdersRecyclerView.isVisible = it.isNullOrEmpty().not()
            orderListAdapter.submitList(it)
        }

        viewModel.isAnyItemSelected.observe(viewLifecycleOwner) {
            if (it) mainActivity()?.changeMainIconToArrow()
            else mainActivity()?.changeMainIconToPlus()
        }
    }

    private fun setRecyclerView() {
        binding.myOrdersRecyclerView.adapter = orderListAdapter
    }

    private fun setItemListener() {
        orderListAdapter.onItemSelectListener = {
            viewModel.setSelectedItem(it)
        }
        orderListAdapter.onItemClickListener = {
            viewModel.navigate(
                MyOrdersFragmentDirections.actionMyOrdersFragmentToDocumentDetailFragment(
                    it
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setIsAnyItemSelected(false)
    }
}