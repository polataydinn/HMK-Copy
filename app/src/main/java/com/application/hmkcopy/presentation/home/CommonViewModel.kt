package com.application.hmkcopy.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.application.hmkcopy.base.BaseViewModel
import com.application.hmkcopy.data.model.CopyCenterItem
import com.application.hmkcopy.data.model.OrderListItem

class CommonViewModel : BaseViewModel() {

    private var _listOfOrders: MutableLiveData<List<OrderListItem>> = MutableLiveData()
    val listOfOrders: LiveData<List<OrderListItem>> get() = _listOfOrders

    private var _isAnyItemSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    val isAnyItemSelected: LiveData<Boolean> get() = _isAnyItemSelected

    private var _copyCenterList: MutableLiveData<List<CopyCenterItem>> = MutableLiveData()
    val copyCenterList: LiveData<List<CopyCenterItem>> get() = _copyCenterList

    fun setMockData() {
        _listOfOrders.value = getMockData()
    }

    fun setCopyCenterListMockData(){
        _copyCenterList.value = getCopyCenterList()
    }

    private fun getCopyCenterList(): List<CopyCenterItem> = listOf(
        CopyCenterItem(
            id = 1,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 2,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 3,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 4,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 5,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 6,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 7,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 8,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 9,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
        CopyCenterItem(
            id = 10,
            centerName = "Online copy center",
            centerAddress = "Fatih Mahallesi Biruni Sokak No :10                    Tel: +90532 123 44 55"
        ),
    )

    fun setSelectedItem(orderListItem: OrderListItem) {
        _listOfOrders.value = _listOfOrders.value?.map {
            if (it == orderListItem) {
                it.copy(isItemSelected = !orderListItem.isItemSelected)
            } else {
                it.copy(isItemSelected = false)
            }
        }
        _isAnyItemSelected.value = listOfOrders.value?.firstOrNull { it.isItemSelected } != null
    }

    private fun getMockData(): List<OrderListItem> = listOf(
        OrderListItem(
            id = 1,
            uploadDate = 1667122608L,
            pageSize = 75,
            documentType = "PDF",
            lessonName = "Tıbbi terminoloji ders notu"
        ),
        OrderListItem(
            id = 2,
            uploadDate = 1667122608L,
            pageSize = 75,
            documentType = "PDF",
            lessonName = "Tıbbi terminoloji ders notu2"
        ),
        OrderListItem(
            id = 3,
            uploadDate = 1667122608L,
            pageSize = 75,
            documentType = "PDF",
            lessonName = "Tıbbi terminoloji ders notu3"
        ),
        OrderListItem(
            id = 4,
            uploadDate = 1667122608L,
            pageSize = 75,
            documentType = "PDF",
            lessonName = "Tıbbi terminoloji ders notu4"
        ),
        OrderListItem(
            id = 5,
            uploadDate = 1667122608L,
            pageSize = 75,
            documentType = "PDF",
            lessonName = "Tıbbi terminoloji ders notu5"
        ),
        OrderListItem(
            id = 6,
            uploadDate = 1667122608L,
            pageSize = 75,
            documentType = "PDF",
            lessonName = "Tıbbi terminoloji ders notu6"
        ),
    )
}