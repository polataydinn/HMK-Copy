package com.application.hmkcopy.presentation.home.copy_center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentConfirmOrderBinding
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.presentation.home.copy_center.adapter.OrderConfirmAdapter
import com.application.hmkcopy.util.extentions.SizeUtils
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
        mainActivity()?.setFabButtonClickListener { 
            if (navController.currentDestination?.label == "fragment_confirm_order"){
                if (binding.confirmOrderCheckbox.isChecked){
                    viewModel.navigate(ConfirmOrderFragmentDirections.actionConfirmOrderFragmentToPaymentWebViewFragment())
                }else{
                    Toast.makeText(
                        requireContext(),
                        "Lütfen mesafeli satış sözleşmesini kabul edin",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        mainActivity()?.setBackButtonListeners {
            if (navController.currentDestination?.label == "fragment_confirm_order"){
                viewModel.navigateBack()
            }
        }
        mainActivity()?.changeMainIconToArrow()
        mainActivity()?.setPageTitle("Siparis Tamamla")
        mainActivity()?.setPageDescInvisible()
    }

    override fun configureObservers() {
        super.configureObservers()
        viewModel.items.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                if (it.size > 5){
                    binding.root.setPadding(0,0,0,SizeUtils.int2dp(requireContext(), 200))
                }
                adapter.submitList(it)
                binding.root.isVisible = true
            }
        }

        viewModel.totalPrice.observe(viewLifecycleOwner){
            binding.confirmOrderTotalPrice.text = "$it TRY"
        }
    }
}