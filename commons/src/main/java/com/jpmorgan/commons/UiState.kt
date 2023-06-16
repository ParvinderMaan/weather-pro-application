package com.jpmorgan.commons



sealed class UiState {

    data class Progress(var msg: String? = null): UiState()
    data class Error(var msg: String? = null, var action: Boolean = true): UiState()
    object Content:UiState()
    data class NoContent(var msg:String?):UiState()
}






