package com.example.petmaps.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.petmaps.data.Mark
import com.example.petmaps.data.repo.MarkRepo
import com.example.petmaps.ui.MarkListState
import com.example.petmaps.ui.map.MapViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MarkerListViewModel(
    private val repository: MarkRepo
) : ViewModel() {
    private val listState =
        MutableStateFlow<MarkListState>(MarkListState.Loading)
    private val saveMarkerState = MutableStateFlow<SaveMarkState>(SaveMarkState.Nothing)


    fun getStateFlow() = listState.asStateFlow()

    fun getList() {
        viewModelScope.launch() {
            try {
                val markers = repository.getMarkers()
                listState.emit(
                    MarkListState.ListSuccess(data = markers)
                )
                if (markers.isEmpty()) {
                    listState.emit(MarkListState.Error(MapViewModel.MESSAGE_EMPTY_LIST))
                }
            } catch (e: Exception) {
                listState.emit(MarkListState.Error(MapViewModel.MESSAGE_LIST_ERROR))
            }
        }
    }

    fun deleteMarker(mark:Mark){
        viewModelScope.launch {
            listState.emit((MarkListState.Loading))
            try {
                repository.delete(mark)
                listState.emit(MarkListState.DeleteSuccess)
            } catch (e:Exception) {
                listState.emit(MarkListState.Error(message = MESSAGE_DONT_FIND_MARK))
            }
        }
    }

    fun save(mark: Mark) {
        viewModelScope.launch {
            saveMarkerState.emit(SaveMarkState.Nothing)
            val markId = repository.create(mark = mark)
            if (markId > 0) {
                saveMarkerState.emit(SaveMarkState.Success(mark))
            } else {
                saveMarkerState.emit(SaveMarkState.Error("cannot save marker"))
            }
        }
    }


    class MarkerListViewModelFactory(private val repo: MarkRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MarkerListViewModel(repo) as T
    }

    companion object {
        const val MESSAGE_DONT_FIND_MARK = "Cannot find marker"
        const val MESSAGE_EMPTY_LIST = "No markers set yet"
        const val MESSAGE_LIST_ERROR = "Loading error"
    }

    sealed class SaveMarkState {
        object Nothing : SaveMarkState()
        data class Success(val mark: Mark) : SaveMarkState()
        data class Error(val message: String) : SaveMarkState()
    }


}