package com.example.petmaps.ui.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.petmaps.R
import com.example.petmaps.app
import com.example.petmaps.data.repo.LocalRepo
import com.example.petmaps.databinding.FragmentMarkerListBinding
import com.example.petmaps.ui.MarkListState
import kotlinx.coroutines.launch

class FragmentMarkerList : Fragment(R.layout.fragment_marker_list) {

    private val binding: FragmentMarkerListBinding by viewBinding()

    private val markerListViewModel: MarkerListViewModel by viewModels {
        MarkerListViewModel.MarkerListViewModelFactory(LocalRepo(requireActivity().app.database.getMarkDao()))
    }

    private val adapter: MarkersAdapter by lazy {
        MarkersAdapter(
            onClickDelete = { marker ->
                markerListViewModel.deleteMarker(marker)
            },
            onClickSave = { marker ->
                markerListViewModel.save(marker)

            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        getList()
        collectListFlow()

    }

    private fun collectListFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                markerListViewModel.getStateFlow().collect { state ->
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
                adapter.submitList(state.data)
            }
            is MarkListState.Error -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            is MarkListState.DeleteSuccess -> {
                markerListViewModel.getList()
            }

        }
    }

    private fun initRecycler() {
        binding.rvMarkerList.apply {
            adapter = this@FragmentMarkerList.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getList() {
        markerListViewModel.getList()
    }

}