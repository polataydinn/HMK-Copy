package com.application.hmkcopy.presentation.home.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.repository.document.DocumentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val repository: DocumentRepository
) : BaseViewModel() {

    private val _webView: MutableLiveData<String> = MutableLiveData()
    val webVew get() = _webView

    fun getWebView() {
        toggleProgress(true)
        viewModelScope.launch {
            val response = repository.getPaymentWebView()
            if (response.isNotEmpty()) {
                _webView.value = response
            }
            toggleProgress(false)
        }
    }
}