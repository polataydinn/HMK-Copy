package com.application.hmkcopy.presentation.home.copy_center

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.application.hmkcopy.R
import com.application.hmkcopy.base.BaseFragment
import com.application.hmkcopy.databinding.FragmentCopyCenterMapBinding
import com.application.hmkcopy.presentation.home.copy_center.adapter.StoreMapInfoWindowAdapter
import com.application.hmkcopy.service.request.CreateCheckoutBasketRequest
import com.application.hmkcopy.service.response.SellersResponseItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CopyCenterMapFragment : BaseFragment<FragmentCopyCenterMapBinding, CopyCenterViewModel>(),
    OnMapReadyCallback {

    override val viewModel: CopyCenterViewModel by viewModels()

    private var googleMap: GoogleMap? = null

    private val args: CopyCenterMapFragmentArgs by navArgs()

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
        viewModel.getSellers()
        setButtonListeners()
        viewModel.basketSuccessful(false)
        subscribeObservers()
        mainActivity()?.makeBottomButtonsInvisible()
    }

    private fun subscribeObservers() {
        viewModel.isCreateBasketSuccessful.observe(viewLifecycleOwner){
            if (it){
                viewModel.navigate(
                    CopyCenterMapFragmentDirections.toConfigurationFragment()
                )
            }
        }
    }

    private fun setButtonListeners() {
        mainActivity()?.setBackButtonListeners {
            if (navController.currentDestination?.label == "fragment_copy_center_map") {
                viewModel.navigateBack()
            }
        }
        mainActivity()?.setFabButtonClickListener {
            if (navController.currentDestination?.label == "fragment_copy_center_map") {
                viewModel.selectedSeller.value?.let { selectedSeller ->
                    if (!selectedSeller.id.isNullOrEmpty() && !args.documentTransferMap.listOfDocuments.isNullOrEmpty()) {
                        val documents = args.documentTransferMap.listOfDocuments?.filter { !it.id.isNullOrEmpty() }?.map { it.id }
                        viewModel.createCheckoutBasket(
                            CreateCheckoutBasketRequest(documents = documents as List<String>),
                            selectedSeller.id
                        )
                    }
                }
            }
        }
    }

    private fun initMap() {
        binding.mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        zoomCurrentTarget()
        fillTheMap()
    }

    private fun fillTheMap() {
        googleMap?.setOnInfoWindowClickListener {
            viewModel.sellers.value?.getOrNull(it.tag as Int)?.let { seller ->
                viewModel.setSelectedSeller(seller)
            }
        }
        googleMap?.setOnInfoWindowCloseListener {
            viewModel.setSelectedSeller(null)
        }
        viewModel.selectedSeller.observe(viewLifecycleOwner) { seller ->
            if (seller != null) {
                openBottomDialog(seller)
                mainActivity()?.changeMainIconToArrow()
            } else {
                mainActivity()?.makeFabButtonToChoose()
                closeBottomDialog()
            }
        }
        viewModel.sellers.observe(viewLifecycleOwner) {
            googleMap?.setInfoWindowAdapter(StoreMapInfoWindowAdapter(it))
            val listOfLatLng = it.map { item -> item.addressCoordinates.toLatLng() }
            val ic = bitmapDescriptorFromVector(requireContext(), R.drawable.ic_copy_center_marker)
            listOfLatLng.forEachIndexed { index, latLng ->
                val options = MarkerOptions()
                    .position(latLng)
                    .icon(ic)

                googleMap?.addMarker(options)?.let { marker ->
                    marker.tag = index
                }

            }
        }
    }


    private fun openBottomDialog(seller: SellersResponseItem) {
        mainActivity()?.bottomSheetContainer?.isVisible = true
        mainActivity()?.setUpBottomSheet(seller)
    }

    private fun closeBottomDialog() {
        mainActivity()?.bottomSheetContainer?.isVisible = false
    }

    private fun zoomCurrentTarget() {
        val coordinatesOfCurrentCenter = arguments?.get("sellerResponseItem") as SellersResponseItem
        val latLng = coordinatesOfCurrentCenter.addressCoordinates.toLatLng()
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latLng,
                13f
            )
        )
    }

    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor? {
        context?.let {
            return ContextCompat.getDrawable(it, vectorResId)?.run {
                setBounds(0, 0, intrinsicWidth, intrinsicHeight)
                val bitmap =
                    Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
                draw(Canvas(bitmap))
                val marker = Bitmap.createScaledBitmap(bitmap, 80, 80, false)
                BitmapDescriptorFactory.fromBitmap(marker)
            }
        }
        return null
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        closeBottomDialog()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        closeBottomDialog()
        binding.mapView.onDestroy()
        mainActivity()?.makeBottomButtonsVisible()
    }
}

private fun String?.toLatLng(): LatLng {
    return LatLng(
        this?.substringBefore(",")?.toDoubleOrNull() ?: 0.0,
        this?.substringAfter(",")?.toDoubleOrNull() ?: 0.0
    )
}