package com.application.hmkcopy.presentation.home.copy_center

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.event.Event
import com.application.hmkcopy.repository.document.DocumentRepository
import com.application.hmkcopy.service.ErrorModel
import com.application.hmkcopy.service.request.CreateCheckoutBasketRequest
import com.application.hmkcopy.service.request.NoteRequest
import com.application.hmkcopy.service.request.SellerPatchRequest
import com.application.hmkcopy.service.response.SellersResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CopyCenterViewModel @Inject constructor(
    private val documentRepository: DocumentRepository
) : BaseViewModel() {
    private val _sellers: MutableLiveData<List<SellersResponseItem>> = MutableLiveData()
    val sellers: LiveData<List<SellersResponseItem>> get() = _sellers


    private val _selectedSeller: MutableLiveData<SellersResponseItem?> = MutableLiveData()
    val selectedSeller: LiveData<SellersResponseItem?> get() = _selectedSeller

    private val mSellers = MutableLiveData<List<SellersResponseItem>>()

    private val _isCreateBasketSuccessful = MutableLiveData(false)
    val isCreateBasketSuccessful: LiveData<Boolean> get() = _isCreateBasketSuccessful

    init {
        _isCreateBasketSuccessful.postValue(false)
    }

    fun getSellers() {
        viewModelScope.launch {
            val response = documentRepository.getAllSellers()
            if (response.apiCallError != null) {
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                mSellers.postValue(response.toList())
                _sellers.postValue(response.toList())
            }
        }
    }

    fun setSelectedSeller(seller: SellersResponseItem?) {
        _selectedSeller.value = seller
    }

    fun searchInSellers(search: String) {
        if (search.isEmpty()) _sellers.value = mSellers.value
        else _sellers.value = mSellers.value?.filter {
            (it.name?.lowercase()?.contains(search.lowercase()) == true ||
                    it.address?.lowercase()?.contains(search.lowercase()) == true)
        }
    }


    fun patchSeller(sellerId: String) {
        viewModelScope.launch {
            val response =
                documentRepository.setSellerPatch(SellerPatchRequest(sellerId = sellerId))
            if (response.apiCallError != null) {
                navigateBack()
                errorMessage.value = Event(ErrorModel(message = response.apiCallError.message))
            } else {
                _isCreateBasketSuccessful.postValue(true)
            }
        }
    }

    fun patchNote(note: String){
        viewModelScope.launch {
            toggleProgress(true)
            val response = documentRepository.addNote(NoteRequest(note = note))
            if (response != null){
                errorMessage.value = Event(ErrorModel(message = response.message))
            } else {
                navigate(CopyCenterEnterNoteFragmentDirections.actionCopyCenterEnterNoteFragmentToConfirmOrderFragment())
            }
            toggleProgress(false)
        }
    }

    fun basketSuccessful(b: Boolean) {
        _isCreateBasketSuccessful.value = b
    }
}