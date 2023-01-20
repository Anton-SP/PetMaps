package com.example.petmaps.ui.marker

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.petmaps.R
import com.example.petmaps.app
import com.example.petmaps.data.Mark
import com.example.petmaps.data.repo.LocalRepo
import com.example.petmaps.databinding.FragmentCreateMarkerBinding
import com.example.petmaps.utils.navigate
import com.example.petmaps.utils.navigationData
import com.google.android.gms.maps.model.LatLng

class FragmentCreateMarker : Fragment(R.layout.fragment_create_marker) {

    private val binding: FragmentCreateMarkerBinding by viewBinding()

    private  val markerViewModel: MarkerViewModel by viewModels {
        MarkerViewModel.MarkerViewModelFactory(LocalRepo(requireActivity().app.database.getMarkDao()))
    }

    private var coordinates: LatLng? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkData()
        initButtons()

    }

    private fun initButtons() {

        binding.btnSave.setOnClickListener {
            saveMarker()
            navigate(R.id.action_fragmentCreateMarker_to_fragmentGMap)
        }

        binding.btnCancel.setOnClickListener {
            navigate(R.id.action_fragmentCreateMarker_to_fragmentGMap)
        }

    }

    private fun saveMarker() {
       val  marker = coordinates?.let {
           Mark(
               id = 0,
               coordinates = it,
               note = binding.edMarkerNote.text.toString()
           )
       }
        marker.let { markerViewModel.save(marker as Mark ) }

    }

    private fun checkData() {
        coordinates = (navigationData as LatLng)
        coordinates?.let { data ->
            with(binding) {
                tvMarkerLatitudeValue.text = data.latitude.toString()
                tvMarkerLongitudeValue.text = data.longitude.toString()
            }

        }
    }

}