package com.example.petmaps.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petmaps.ui.MarkListState
import com.example.petmaps.utils.PermissionUtils
import com.example.petmaps.utils.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import com.example.petmaps.R
import com.example.petmaps.app
import com.example.petmaps.data.Mark
import com.example.petmaps.data.repo.LocalRepo
import com.example.petmaps.utils.navigate

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.launch

class FragmentGMap :
    Fragment(),
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    OnMarkerClickListener,
    OnMarkerDragListener,
    OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback {

    private var permissionDenied = false

    private lateinit var map: GoogleMap

    private val mapViewModel: MapViewModel by viewModels {
        MapViewModel.MapViewModelFactory(LocalRepo(requireActivity().app.database.getMarkDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_g_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(callbackMap: GoogleMap) {
        setMap(callbackMap)
        getMarkerList()
        collectListFlow()
        callbackMap.setOnMyLocationButtonClickListener(this)
        callbackMap.setOnMyLocationClickListener(this)
        enableMyLocation()
    }



    private fun collectListFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mapViewModel.getStateFlow().collect { state ->
                    checkListState(state)
                }
            }
        }
    }

    private fun checkListState(state: MarkListState) {
        when (state) {
            is MarkListState.Loading -> {
                Toast.makeText(requireContext(), "loading marker list", Toast.LENGTH_SHORT).show()
            }
            is MarkListState.ListSuccess -> {
                addMark(state.data)
            }
            is MarkListState.Error -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            is MarkListState.DeleteSuccess -> {
                Toast.makeText(requireContext(), "delete done", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun getMarkerList() {
        mapViewModel.getMarkList()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireContext(), "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(requireContext(), "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }


    private fun setMap(callbackMap: GoogleMap) {
        map = callbackMap
        with(map) {
            setPadding(0, 0, 12, 0)
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMapToolbarEnabled = true
            setOnMarkerClickListener(this@FragmentGMap)
            setOnMapClickListener { coordinates ->
                navigate(R.id.action_fragmentGMap_to_fragmentCreateMarker, coordinates)
            }
        }
    }

    private fun addMark(markers: List<Mark>) {
        map.clear()
        markers.forEach() { mark ->
            map.addMarker(MarkerOptions().position(mark.coordinates))

        }
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {

        // [START maps_check_location_permission]
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            return
        }

        // 2. If if a permission rationale dialog should be shown
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) || ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            PermissionUtils.RationaleDialog.newInstance(
                LOCATION_PERMISSION_REQUEST_CODE, true
            ).show(this.parentFragmentManager, "dialog")
            return
        }

        // 3. Otherwise, request permission
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
        // [END maps_check_location_permission]
    }


    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    private fun showMissingPermissionError() {
        newInstance(true).show(this.parentFragmentManager, "dialog")
    }

    companion object {

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Toast.makeText(
            requireContext(), " marker lat-long ${marker.position}",
            Toast.LENGTH_SHORT
        ).show()
        return false
    }

    override fun onMarkerDrag(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragEnd(p0: Marker) {
        TODO("Not yet implemented")
    }

    override fun onMarkerDragStart(p0: Marker) {
        TODO("Not yet implemented")
    }
}

