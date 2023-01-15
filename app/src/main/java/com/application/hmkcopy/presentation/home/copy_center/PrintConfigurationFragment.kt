package com.application.hmkcopy.presentation.home.copy_center

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.PrintConfigurationFragmentBinding
import com.application.hmkcopy.presentation.home.copy_center.adapter.PrintListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrintConfigurationFragment :
    BaseFragment<PrintConfigurationFragmentBinding, PrintConfigurationViewModel>() {
    override val viewModel: PrintConfigurationViewModel by viewModels()
    private val adapter: PrintListAdapter by lazy {
        PrintListAdapter()
    }

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PrintConfigurationFragmentBinding {
        return PrintConfigurationFragmentBinding.inflate(layoutInflater, container, false)
    }

    override fun updateUI() {
        binding.printConfigurationRecyclerView.adapter = adapter
        mainActivity()?.setPageDesc("Baskı Merkezi")
        mainActivity()?.setPageTitle("Online Copy Center")
        mainActivity()?.setBackButtonListeners {
            if (navController.currentDestination?.label == "print_configuration_fragment") {
                viewModel.navigateBack()
            }
        }
        mainActivity()?.setFabButtonClickListener {
            if (navController.currentDestination?.label == "print_configuration_fragment") {
                viewModel.navigate(PrintConfigurationFragmentDirections.actionPrintConfigurationFragmentToConfirmOrderFragment())
            }
        }
    }

    override fun configureObservers() {
        super.configureCallbacks()
        viewModel.checkout()
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun configureCallbacks() {
        adapter.onCheckStateChanged = { isChecked, itemId, options ->
            viewModel.updateBasket(options, itemId, isChecked = isChecked)
        }
        adapter.onItemSelectedListener = { text, itemId, options ->
            viewModel.updateBasket(options, itemId, text = text)
        }

        adapter.onDocumentDownload = {

        }

        adapter.isExpanded = {
            viewModel.updateExpandData(it)
        }
    }
}