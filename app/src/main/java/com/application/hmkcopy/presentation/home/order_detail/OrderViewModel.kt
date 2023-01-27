package com.application.hmkcopy.presentation.home.order_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.repository.document.DocumentRepository
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.service.response.OrdersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: DocumentRepository
) : BaseViewModel() {

    private val _orders: MutableLiveData<OrdersResponse> = MutableLiveData()
    val orders : LiveData<OrdersResponse> get() = _orders

    fun getOrderedItems(){
        viewModelScope.launch {
            toggleProgress(true)
            val response = repository.getOrderedItems()
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                _orders.postValue(response)
            }
            toggleProgress(false)
        }
    }
}