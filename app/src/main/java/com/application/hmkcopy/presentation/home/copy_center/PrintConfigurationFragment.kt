package com.application.hmkcopy.presentation.home.copy_center

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.PrintConfigurationFragmentBinding

class PrintConfigurationFragment :
    BaseFragment<PrintConfigurationFragmentBinding, PrintConfigurationViewModel>() {
    override val viewModel: PrintConfigurationViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): PrintConfigurationFragmentBinding {
        return PrintConfigurationFragmentBinding.inflate(layoutInflater, container, false)
    }

    override fun updateUI() {
        val dd = DropDownView(requireContext())
        dd.text1 = "hello world"
        dd.text2 = "bottom"
        dd.items = "HELLLO".map { it.toString() }
        binding.container.printSettingsLinearContainer.addView(dd)
        binding.container.printSettingsLinearContainer.addView(SelectableView(requireContext()).apply {
            text1 = "hello aydin"
            text2 = "something"
            isChecked = true
        })
        print("a")
    }
}