package com.application.hmkcopy.presentation.home.copy_center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.data.model.CopyCenterItem
import com.application.hmkcopy.databinding.FragmentCopyCenterChooserBinding
import com.application.hmkcopy.presentation.home.CommonViewModel
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.presentation.home.copy_center.adapter.CopyCenterAdapter
import com.application.hmkcopy.presentation.home.my_orders.MyOrdersFragmentDirections

class CopyCenterChooserFragment :
    BaseFragment<FragmentCopyCenterChooserBinding, CommonViewModel>() {
    override val viewModel: CommonViewModel by viewModels()

    private val adapter: CopyCenterAdapter by lazy { CopyCenterAdapter() }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCopyCenterChooserBinding {
        return FragmentCopyCenterChooserBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setCopyCenterListMockData()
        setHeader()
        setAdapter()
        setObservers()
        setButtonListeners()
    }

    private fun setAdapter() {
        binding.copyCenterRecyclerView.adapter = adapter
        adapter.onItemClick = {
            findNavController().navigate(R.id.action_copyCenterChooserFragment_to_copyCenterMapFragment)
        }
    }

    private fun setObservers() {
        viewModel.copyCenterList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

    private fun setButtonListeners() {
        (activity as MainActivity).setBackButtonListeners {
            if (navController.currentDestination?.label == "fragment_copy_center_chooser") {
                findNavController().navigateUp()
            }
        }
    }

    private fun setHeader() {
        (activity as MainActivity).apply {
            setPageTitle("Baskı Merkezini Seç")
            setBackButtonVisible()
        }
    }
}