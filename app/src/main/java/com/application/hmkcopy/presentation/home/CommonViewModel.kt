package com.application.hmkcopy.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.data.model.CopyCenterItem
import com.application.hmkcopy.data.model.OrderListItem
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.presentation.authentication.OTPinFragmentDirections
import com.application.hmkcopy.repository.document.DocumentRepository
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.service.response.DocumentsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : BaseViewModel() {

    private var _isAnyItemSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAnyItemSelected: LiveData<Boolean> get() = _isAnyItemSelected

    private val _documents = MutableLiveData<List<DocumentsResponse.Documents.Result>?>()
    val documents get() = _documents

    val isGetDocument = MutableLiveData(false)

    fun setSelectedItem(document: DocumentsResponse.Documents.Result) {
        _documents.value = _documents.value?.map {
            if (it == document) {
                it.copy(isItemSelected = !document.isItemSelected)
            } else {
                it.copy()
            }
        }
        _isAnyItemSelected.value = _documents.value?.firstOrNull { it.isItemSelected } != null
    }

    fun getDocuments() {
        viewModelScope.launch {
            val response = documentRepository.getDocuments()
            if (response.apiCallError == null) {
                isGetDocument.postValue(true)
                _documents.postValue(response.documents?.results as List<DocumentsResponse.Documents.Result>?)
            } else {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            }
        }

    }

    fun deleteDocument(id: String?) {
        viewModelScope.launch {
            val response = id?.let { documentRepository.deleteDocument(it) }
            if (response != null) {
                errorMessage.value = Event(ErrorModel(message = response.message))
            } else {
                navigateBack()
            }
        }
    }
    fun setIsAnyItemSelected(isItemSelected: Boolean){
        _isAnyItemSelected.postValue(isItemSelected)
    }
}