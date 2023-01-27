package com.application.hmkcopy.presentation.home.copy_center

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentCopyCenterChooserBinding
import com.application.hmkcopy.presentation.home.MainActivity
import com.application.hmkcopy.presentation.home.copy_center.adapter.CopyCenterAdapter
import com.application.hmkcopy.service.request.CreateCheckoutBasketRequest
import com.application.hmkcopy.util.AppPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CopyCenterChooserFragment :
    BaseFragment<FragmentCopyCenterChooserBinding, CopyCenterViewModel>() {
    override val viewModel: CopyCenterViewModel by viewModels()

    private val adapter: CopyCenterAdapter by lazy { CopyCenterAdapter() }
    private val args: CopyCenterChooserFragmentArgs by navArgs()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCopyCenterChooserBinding {
        return FragmentCopyCenterChooserBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSellers()
        setHeader()
        setAdapter()
        setObservers()
        setButtonListeners()
        setFabButton()
        mainActivity()?.setPageDescInvisible()
    }

    private fun setFabButton() {
        mainActivity()?.makeFabButtonToChoose()
        mainActivity()?.makeBottomButtonsInvisible()
    }

    private fun setAdapter() {
        binding.copyCenterRecyclerView.adapter = adapter
        adapter.onShowInMapClick = {
            if (!AppPermission.permissionGranted(requireContext())) {
                mainActivity()?.let { mainActivity -> AppPermission.requestPermission(mainActivity) }
                Toast.makeText(
                    requireContext(),
                    "Konum izini vermeniz gerekmektedir.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.navigate(
                    CopyCenterChooserFragmentDirections.actionCopyCenterChooserFragmentToCopyCenterMapFragment(
                        it,
                        args.documentTransfer
                    )
                )
            }
        }
        adapter.onItemClick = {
            if (!it.id.isNullOrEmpty() && !args.documentTransfer.listOfDocuments.isNullOrEmpty()) {
                viewModel.patchSeller(it.id)
            }
        }
    }

    private fun setObservers() {
        viewModel.sellers.observe(viewLifecycleOwner) {
            adapter.clear()
            adapter.submitList(it)
        }
        viewModel.isCreateBasketSuccessful.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.navigate(
                    CopyCenterChooserFragmentDirections.actionCopyCenterChooserFragmentToPrintConfigurationFragment()
                )
                viewModel.basketSuccessful(false)
            }
        }
    }

    private fun setButtonListeners() {
        mainActivity()?.setBackButtonListeners {
            if (navController.currentDestination?.label == "fragment_copy_center_chooser") {
                viewModel.navigateBack()
            }
        }
        binding.copyCenterSearchEditText.addTextChangedListener {
            viewModel.searchInSellers(it.toString())
        }
    }

    private fun setHeader() {
        (activity as MainActivity).apply {
            setPageTitle("Baskı Merkezini Seç")
            setBackButtonVisible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity()?.makeFabButtonToLeftArrow()
        mainActivity()?.makeBottomButtonsVisible()
    }
}