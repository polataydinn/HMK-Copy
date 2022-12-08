package com.application.hmkcopy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentOrderNoteBinding

class OrderNoteFragment : BaseFragment<FragmentOrderNoteBinding, CommonViewModel>() {
    override val viewModel: CommonViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderNoteBinding {
        return FragmentOrderNoteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}