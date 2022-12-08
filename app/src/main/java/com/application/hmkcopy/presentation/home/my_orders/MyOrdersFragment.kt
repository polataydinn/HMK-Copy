package com.application.hmkcopy.presentation.home.my_orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentMyOrdersBinding
import com.application.hmkcopy.presentation.home.CommonViewModel
import com.application.hmkcopy.presentation.home.MainActivity

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
        viewModel.setMockData()
        setHeaders()
        setRecyclerView()
        setObservers()
        setItemListener()
        setButtonListeners()
        mainActivity()?.changeMainIconToArrow()
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
                findNavController().navigate(
                    MyOrdersFragmentDirections.actionMyOrdersFragmentToCopyCenterChooserFragment()
                )
            }
        }
    }

    private fun setObservers() {
        viewModel.listOfOrders.observe(viewLifecycleOwner) {
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
            Toast.makeText(requireContext(), "click seçildi", Toast.LENGTH_SHORT).show()
        }
    }
}