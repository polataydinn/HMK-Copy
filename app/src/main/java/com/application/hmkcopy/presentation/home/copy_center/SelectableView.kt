package com.application.hmkcopy.presentation.home.copy_center

import android.content.Context
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.application.hmkcopy.databinding.SelectectableViewBinding

class SelectableView(
    context: Context
) : ConstraintLayout(context) {
    private val binding: SelectectableViewBinding
    var id: String = ""
    var onCheckStateChanged : (String, Boolean) -> Unit = {_,_ -> }

    init {
        binding = SelectectableViewBinding.inflate(LayoutInflater.from(context), this, true)
        setOnCheckboxSelected()
    }

    private fun setOnCheckboxSelected() {
        binding.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            onCheckStateChanged(id, isChecked)
        }
    }

    var text1: String = ""
        set(value) {
            binding.text1.text = value
            field = value
        }
    var text2: String = ""
        set(value) {
            binding.text2.text = value
            field = value
        }

    var isChecked = false
        set(value) {
            binding.checkbox.isChecked = value
            field = value
        }
        get() = binding.checkbox.isChecked

}