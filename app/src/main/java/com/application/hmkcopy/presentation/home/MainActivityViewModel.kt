package com.application.hmkcopy.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.repository.document.DocumentRepository
import com.application.hmkcopy.repository.user.UserRepository
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.service.request.UploadVerifyRequest
import com.application.hmkcopy.service.response.DocumentsResponse
import com.application.hmkcopy.service.response.UserInfoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val documentRepository: DocumentRepository,
    private val repository: UserRepository
) : BaseViewModel() {

    private val _documents = MutableLiveData<DocumentsResponse>()
    val documents get() = _documents

    private val _userData : MutableLiveData<UserInfoResponse> = MutableLiveData()
    val userData : LiveData<UserInfoResponse> get() = _userData

    val isChooseSelected = MutableLiveData<Boolean>()

    init {
        isChooseSelected.postValue(true)
    }

    val isDialogDisable = MutableLiveData(false)




    fun getUserData(){
        viewModelScope.launch {
            val response = repository.getUserData()
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                _userData.postValue(response)
            }
        }
    }

    fun uploadDocument(file: File, name: String) {
        toggleProgress(true)
        viewModelScope.launch {
            val checkResponse = documentRepository.uploadDocument(
                name = name,
            )
            if (checkResponse.error != null) {
                errorMessage.value = Event(ErrorModel(message = checkResponse.error.message))
            } else if (!checkResponse.presignedUrl?.url.isNullOrEmpty() && !checkResponse.presignedUrl?.key.isNullOrEmpty()) {
                uploadImageWithNewUrl(
                    file = file,
                    url = checkResponse.presignedUrl?.url?.substringAfter(".amazonaws.com/") ?: "",
                    key = checkResponse.presignedUrl?.key ?: "",
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
}