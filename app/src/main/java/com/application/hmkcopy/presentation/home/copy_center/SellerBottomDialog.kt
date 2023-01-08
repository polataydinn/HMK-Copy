package com.application.hmkcopy.presentation.home.copy_center

import android.view.LayoutInflater
import android.view.ViewGroup
import com.application.hmkcopy.base.BaseBottomSheetFragment
import com.application.hmkcopy.databinding.SellerBottomSheetBinding
import com.application.hmkcopy.service.response.SellersResponseItem

class SellerBottomDialog(
    private val seller: SellersResponseItem
) : BaseBottomSheetFragment<SellerBottomSheetBinding>() {

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): SellerBottomSheetBinding {
        return SellerBottomSheetBinding.inflate(layoutInflater, container, false)
    }

    override fun updateUI() {
        super.updateUI()
        binding.apply {

        }
    }


}