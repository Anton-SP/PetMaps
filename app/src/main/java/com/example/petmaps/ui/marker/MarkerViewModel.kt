package com.example.petmaps.ui.marker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.petmaps.data.Mark
import com.example.petmaps.data.repo.MarkRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MarkerViewModel(
    private val repository: MarkRepo
) : ViewModel() {
    private val createMarkState =
        MutableStateFlow<MarkerViewModel.CreateMarkState>(MarkerViewModel.CreateMarkState.Nothing)

    fun save(mark: Mark) {
        viewModelScope.launch {
            createMarkState.emit(MarkerViewModel.CreateMarkState.Nothing)
            val markId = repository.create(mark = mark)
            if (markId > 0) {
                createMarkState.emit(MarkerViewModel.CreateMarkState.Success(mark))
            } else {
                createMarkState.emit(MarkerViewModel.CreateMarkState.Error("cannot save marker"))
            }
        }
    }

    class MarkerViewModelFactory(private val repo: MarkRepo) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MarkerViewModel(repo) as T
    }

    sealed class CreateMarkState {
        object Nothing : CreateMarkState()
        data class Success(val mark: Mark) : CreateMarkState()
        data class Error(val message: String) : CreateMarkState()
    }
}