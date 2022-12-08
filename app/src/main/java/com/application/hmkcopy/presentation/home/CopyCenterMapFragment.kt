package com.application.hmkcopy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentCopyCenterMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class CopyCenterMapFragment : BaseFragment<FragmentCopyCenterMapBinding, CommonViewModel>(), OnMapReadyCallback{

    override val viewModel: CommonViewModel by viewModels()

    private var googleMap: GoogleMap? = null

    override fun layoutResource(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCopyCenterMapBinding {
        return FragmentCopyCenterMapBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        initMap()
    }

    private fun initMap() {
        binding.mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }
}