package com.application.hmkcopy.presentation.home.copy_center.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import com.application.hmkcopy.app.MyApplication
import com.application.hmkcopy.databinding.MainStoreLocatorInfoItemBinding
import com.application.hmkcopy.service.response.SellersResponseItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import java.util.*

class StoreMapInfoWindowAdapter(
    private val storeList: List<SellersResponseItem>
) : GoogleMap.InfoWindowAdapter {

    private var binding: MainStoreLocatorInfoItemBinding

    init {
        val layoutInflater = LayoutInflater.from(MyApplication.instance)
        binding = MainStoreLocatorInfoItemBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    private fun bindView(marker: Marker) {
        val index = (marker.tag as? Int) ?: 0
        val store = storeList.getOrNull(index) ?: return
        binding.apply {
            text.text = store.name
            text2.text = store.address?.substring(0,50)
            text3.text = store.phone
        }
    }

    override fun getInfoContents(marker: Marker): View {
        bindView(marker)
        return binding.root
    }

    override fun getInfoWindow(marker: Marker): View {
        bindView(marker)
        return binding.root
    }

}