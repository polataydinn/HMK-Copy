package com.application.hmkcopy.presentation.home.copy_center

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.repository.document.DocumentRepository
import com.application.hmkcopy.service.ErrorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrintConfigurationViewModel @Inject constructor(
    private val repository: DocumentRepository
) : BaseViewModel() {

    private val _items = MutableLiveData<List<ProductListItem>>()
    val items: LiveData<List<ProductListItem>> get() = _items

    fun checkout() {
        viewModelScope.launch {
            val response = repository.checkout()
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                _items.value = response.toDomain()
            }
        }
    }
}