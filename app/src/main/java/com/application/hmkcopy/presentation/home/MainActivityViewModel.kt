package com.application.hmkcopy.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.repository.document.DocumentRepository
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.service.request.UploadVerifyRequest
import com.application.hmkcopy.service.response.DocumentsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    private val _documents = MutableLiveData<DocumentsResponse>()
    val documents get() = _documents

    val isDialogDisable = MutableLiveData(false)

    fun uploadDocument(file: File, name: String) {
        viewModelScope.launch {
            val checkResponse = documentRepository.uploadDocument(
                name = name,
            )
            if (checkResponse.error != null) {
                errorMessage.value = Event(ErrorModel(message = checkResponse.error.message))
            } else if (checkResponse.url != null && checkResponse.key != null) {
                uploadImageWithNewUrl(
                    file = file,
                    url = checkResponse.url.substringAfter(".amazonaws.com/"),
                    key = checkResponse.key,
                    name = name
                )
            }
        }
    }


    private fun uploadImageWithNewUrl(file: File, url: String, key: String, name: String) {
        viewModelScope.launch {
            val checkResponse = documentRepository.uploadImageWithNewUrl(
                url = url,
                part = file.readBytes()
            )
            if (checkResponse.isSuccessful) {
                uploadVerify(name = name, key = key)
            } else {
                errorMessage.value = Event(ErrorModel(message = checkResponse.message()))
            }
        }
    }

    private suspend fun uploadVerify(name: String, key: String) {
        val response = documentRepository.uploadVerify(
            UploadVerifyRequest(
                name = name,
                key = key
            )
        )
        if (response.apiCallError == null) {
            isDialogDisable.postValue(true)
        } else {
            errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
        }
    }

    private fun getDocuments() {
        viewModelScope.launch {
            val response = documentRepository.getDocuments()
            if (response.apiCallError == null) {
                _documents.postValue(response)

            } else {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            }
        }

    }
}