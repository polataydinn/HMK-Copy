package com.application.hmkcopy.presentation.home.order_detail

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.application.hmkcopy.databinding.OrderedLinearRowBinding

class OrderDetailView(
    context: Context
) : ConstraintLayout(context) {
    private val binding: OrderedLinearRowBinding

    init {
        binding = OrderedLinearRowBinding.inflate(LayoutInflater.from(context), this, true)
    }

    var name: String = ""
        set(value) {
            binding.name.text = value
            field = value
        }
    var value: String = ""
        set(value) {
            binding.value.text = value
            field = value
        }

    var isChecked = false
        set(value) {
            binding.isVisible.isVisible = value
            field = value
        }
        get() = binding.isVisible.isVisible
}