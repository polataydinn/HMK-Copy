package com.application.hmkcopy.presentation.home.copy_center

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.presentation.home.copy_center.adapter.PrintViewOptions
import com.application.hmkcopy.repository.document.DocumentRepository
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.service.response.CheckoutResponse
import com.application.hmkcopy.util.extentions.toSquaredPaper
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
        toggleProgress(true)
        viewModelScope.launch {
            val response = repository.checkout()
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                getBasketOptions(response.toDomain())
            }
            toggleProgress(false)
        }
    }

    private fun getBasketOptions(checkout: List<ProductListItem>) {
        viewModelScope.launch {
            val response = repository.getBasketOptions()
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                val mCheckout = mutableListOf<ProductListItem>()
                checkout.forEach { mCheckout.add(it.copy(basketOptions = response.printOptions)) }
                _items.value = mCheckout
            }
        }
    }

    private fun updateBasketRequest(
        itemId: String,
        updateBasketRequest: CheckoutResponse.Checkout.Product.PrintOptions
    ) {
        toggleProgress(true)
        viewModelScope.launch {
            val response = repository.updateBasket(itemId, updateBasketRequest)
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                val mCheckout = mutableListOf<ProductListItem>()
                response.toDomain()
                    .forEachIndexed { index, responseItem ->
                        items.value?.get(index)?.basketOptions?.let {
                            responseItem.copy(
                                basketOptions = it,
                                isExpanded = _items.value?.get(index)?.isExpanded ?: false
                            )
                        }
                            ?.let { mCheckout.add(it) }
                    }
                _items.value = mCheckout
                toggleProgress(false)
            }
        }
    }


    fun updateBasket(
        options: PrintViewOptions,
        id: String,
        isChecked: Boolean = false,
        text: String = ""
    ) {
        val mItems = _items.value?.filter { it.id == id }?.get(0)?.printOptions

        when (options) {
            PrintViewOptions.PAPER_SIZE -> {
                if (text.isNotEmpty()) {
                    mItems?.copy(paperSize = text)?.let { updateBasketRequest(id, it) }
                }
            }
            PrintViewOptions.ORIENTATION -> {
                if (text.isNotEmpty()) {
                    mItems?.copy(paperDirection = text.substringBefore(" "))?.let { updateBasketRequest(id, it) }
                }
            }
            PrintViewOptions.SIDE -> {
                if (text.isNotEmpty() && text == "Tek Yönlü Yazdır") {
                    mItems?.copy(paperSide = 1)?.let { updateBasketRequest(id, it) }
                } else {
                    mItems?.copy(paperSide = 2)?.let { updateBasketRequest(id, it) }
                }
            }
            PrintViewOptions.SQUARED -> {
                if (text.isNotEmpty()) {
                    mItems?.copy(paperSquare = text.toSquaredPaper())
                        ?.let { updateBasketRequest(id, it) }
                }
            }
            PrintViewOptions.COLORED -> {
                mItems?.copy(isColored = isChecked)?.let { updateBasketRequest(id, it) }
            }
            PrintViewOptions.SPIRALLED -> {
                mItems?.copy(isSpiralled = isChecked)?.let { updateBasketRequest(id, it) }
            }
        }
    }

    fun updateExpandData(item: ProductListItem) {
        _items.value =
            _items.value?.map { if (it == item) it.copy(isExpanded = it.isExpanded.not()) else it }
    }
}
