package com.application.hmkcopy.presentation.home.copy_center

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.application.hmkcopy.databinding.PriceListViewBinding

class PriceListView(
    context: Context
) : ConstraintLayout(context){
    private val binding: PriceListViewBinding

    init {
        binding = PriceListViewBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var orderName: String = ""
        set(value) {
            binding.orderName .text= value
            field = value
        }
    var orderPrice: String = ""
        set(value) {
            binding.orderPrice.text = value
            field = value
        }
}