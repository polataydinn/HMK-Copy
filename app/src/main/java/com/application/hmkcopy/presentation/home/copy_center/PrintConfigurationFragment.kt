package com.application.hmkcopy.presentation.home.copy_center

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
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
    }

    override fun configureObservers() {
        super.configureCallbacks()
        viewModel.checkout()
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    override fun configureCallbacks() {
        adapter.onCheckStateChanged = { isChecked, id ->
            Toast.makeText(requireContext(), "isChecked : $isChecked", Toast.LENGTH_SHORT).show()
        }
        adapter.onItemSelectedListener = { text, position, id ->
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        }

        adapter.onDocumentDownload = {

        }
    }
}