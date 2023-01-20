package com.example.petmaps.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.petmaps.MarkListState
import com.example.petmaps.data.Mark
import com.example.petmaps.data.repo.MarkRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(
    private val repository: MarkRepo
) : ViewModel() {

    //private val createMarkState = MutableStateFlow<CreateMarkState>(CreateMarkState.Nothing)

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


    fun save(mark: Mark) {
       /* viewModelScope.launch {
            createMarkState.emit(CreateMarkState.Nothing)
            val markId = repository.create(mark = mark)
            if (markId > 0) {
                createMarkState.emit(CreateMarkState.Success(mark))
            } else {
                createMarkState.emit(CreateMarkState.Error("Не удалось сохранить маркер игру"))
            }
        }*/

    }

    companion object {
        const val MESSAGE_DONT_FIND_MARK = "Cannot find marker"
        const val MESSAGE_EMPTY_LIST = "No markers set yet"
        const val MESSAGE_LIST_ERROR = "Loading error"
    }

    class MapViewModelFactory(private val repo: MarkRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MapViewModel(repo) as T
    }

    /*sealed class CreateMarkState {
     //   object Nothing : CreateMarkState()
        data class Success(val mark: Mark) : CreateMarkState()
        data class Error(val message: String) : CreateMarkState()
    }*/
}
