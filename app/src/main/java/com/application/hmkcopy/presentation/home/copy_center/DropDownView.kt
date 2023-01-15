package com.application.hmkcopy.presentation.home.copy_center

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.application.hmkcopy.R
import com.application.hmkcopy.databinding.SpinnerViewBinding

class DropDownView(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {
    val binding: SpinnerViewBinding
    var onItemSelectedListener: (String, Int) -> Unit = { _, _ -> }
    private var adapter = ArrayAdapter<String>(context, R.layout.drop_down_view, R.id.spinner_text)
    private var ignoreFirstSelect: Boolean = false

    var title: String = ""
        set(value) {
            binding.text1.text = value
            field = value
        }

    var description: String = ""
        set(value) {
            binding.text2.text = value
            field = value
        }


    var items: List<String> = listOf()
        set(value) {
            field = value
            setItems()
        }
    var arrowVisibility: Boolean = true
        set(value) {
            binding.arrowDown.isVisible = value
            binding.spinner.isEnabled = value
            binding.spinner.isClickable = value
            field = value
        }

    init {
        binding = SpinnerViewBinding.inflate(LayoutInflater.from(context), this, true)
        binding.spinner.adapter = adapter
        // maybe we could set this onDraw method, for sake of recycling.
        spinnerItemSelectListener()
    }

    private fun setItems() {
        adapter.clear()
        adapter.addAll(items)
    }


    private fun spinnerItemSelectListener() {
        binding.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (ignoreFirstSelect){
                    onItemSelectedListener(items[position], position)
                    title = items[position]
                }
                ignoreFirstSelect = true
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }

        }
    }



}