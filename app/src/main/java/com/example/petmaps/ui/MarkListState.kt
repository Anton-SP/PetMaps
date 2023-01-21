package com.example.petmaps.ui

import com.example.petmaps.data.Mark

sealed class MarkListState {

    data class ListSuccess(val data: List<Mark>) : MarkListState()

    object DeleteSuccess : MarkListState()

    data class Error(val message: String) : MarkListState()

    object Loading : MarkListState()
}
