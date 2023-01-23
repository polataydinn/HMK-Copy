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

    private val _orders: MutableLiveData<List<OrdersResponse.Order?>> = MutableLiveData()
    val orders : LiveData<List<OrdersResponse.Order?>> get() = _orders

    fun getOrderedItems(){
        viewModelScope.launch {
            val response = repository.getOrderedItems()
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                response.orders?.let {
                    _orders.postValue(it)
                }
            }
        }
    }
}