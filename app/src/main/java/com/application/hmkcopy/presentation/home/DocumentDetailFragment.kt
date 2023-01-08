package com.application.hmkcopy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentDocumentDetailBinding
import com.application.hmkcopy.service.response.DocumentsResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DocumentDetailFragment : BaseFragment<FragmentDocumentDetailBinding, CommonViewModel>() {
    override val viewModel: CommonViewModel by viewModels()

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDocumentDetailBinding {
        return FragmentDocumentDetailBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val document = arguments?.get("documentItem") as DocumentsResponse.Documents.Result
        setUI(document)
        setButtonListeners(document.id)
    }

    private fun setUI(document: DocumentsResponse.Documents.Result) {
        binding.documentsDetailDocId.text = document.id
        binding.documentsDetailDocumentType.text = document.name?.substringAfterLast(".")
        binding.documentDetailLessonName.text = document.name?.substringBeforeLast(".")
        binding.documentDetailUploadDate.text = document.uploadDate?.substring(0,10)
        binding.documentsDetailPageCount.text = document.pageCount.toString()
    }

    private fun setButtonListeners(id: String?) {
        binding.documentDetailSendCenterButton.setOnClickListener {
            viewModel.navigate(DocumentDetailFragmentDirections.actionDocumentDetailFragmentToCopyCenterChooserFragment())
        }
        binding.documentDetailDeleteButton.setOnClickListener {
            viewModel.deleteDocument(id)
        }
        binding.documentDetailChooseDocButton.setOnClickListener {
            viewModel.navigateBack()
        }
    }
}