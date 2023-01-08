package com.application.hmkcopy.presentation.home.copy_center

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.application.hmkcopy.databinding.PriceRowBinding
import com.application.hmkcopy.service.response.Paper

class PriceRowView(
    context: Context,
    paper: Paper
) : FrameLayout(context) {
    private var binding: PriceRowBinding

    init {
        binding = PriceRowBinding.inflate(LayoutInflater.from(context), this, true)

        binding.name.text = paper.color + " " + paper.side
        binding.value.text = ":  ${paper.price} TL"
    }


}