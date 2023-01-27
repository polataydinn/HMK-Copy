package com.application.hmkcopy.presentation.home.copy_center

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentCopyCenterEnterNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CopyCenterEnterNoteFragment :
    BaseFragment<FragmentCopyCenterEnterNoteBinding, CopyCenterViewModel>() {
    override val viewModel: CopyCenterViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCopyCenterEnterNoteBinding {
        return FragmentCopyCenterEnterNoteBinding.inflate(inflater, container, false)
    }

    override fun updateUI() {
        super.updateUI()
        mainActivity()?.setFabButtonClickListener {
            if (navController.currentDestination?.label == "fragment_copy_center_enter_note") {
                if (binding.addNotesText.text.toString().isNotEmpty()) {
                    viewModel.patchNote(binding.addNotesText.text.toString())
                } else {
                    viewModel.navigate(CopyCenterEnterNoteFragmentDirections.actionCopyCenterEnterNoteFragmentToConfirmOrderFragment())
                }
            }
        }
    }
}