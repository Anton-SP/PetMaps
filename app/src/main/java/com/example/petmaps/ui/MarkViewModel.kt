package com.example.petmaps.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.petmaps.MarkListState
import com.example.petmaps.data.repo.MarkRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MarkViewModel(
    private val repository: MarkRepo
) : ViewModel() {

    val stateFlow = MutableStateFlow<MarkListState>(MarkListState.Loading)

    fun getStateFlow() = stateFlow.asStateFlow()

    fun getMarkList() {
        viewModelScope.launch() {
            try {
                val markers = repository.getMarkers()
                stateFlow.emit(
                    MarkListState.ListSuccess(data = markers)
                )
                if (markers.isEmpty()) {
                    stateFlow.emit(MarkListState.Error(MESSAGE_EMPTY_LIST))
                }
            } catch (e: Exception) {
                stateFlow.emit(MarkListState.Error(MESSAGE_LIST_ERROR))
            }
        }

    }

    companion object {
        const val MESSAGE_DONT_FIND_MARK = "Cannot find marker"
        const val MESSAGE_EMPTY_LIST = "No markers set yet"
        const val MESSAGE_LIST_ERROR = "Loading error"
    }

    class MarkViewModelFactory(private val repo: MarkRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MarkViewModel(repo) as T
    }
}
