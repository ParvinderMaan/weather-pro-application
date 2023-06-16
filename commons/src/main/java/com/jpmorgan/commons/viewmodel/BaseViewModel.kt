package com.jpmorgan.commons.viewmodel

import android.os.Looper
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jpmorgan.commons.UiState
import com.jpmorgan.commons.utils.SingleLiveEvent

open class BaseViewModel : ViewModel() {
	private fun isThreadMain() = (Looper.myLooper() == Looper.getMainLooper())

    /*
     * Toast
     */
    private val _toastResLiveData = SingleLiveEvent<Int>()
    fun onShowToastResource(): LiveData<Int> = _toastResLiveData

    private val _toastStrLiveData = SingleLiveEvent<String>()
    fun onShowToastNonResource(): LiveData<String> = _toastStrLiveData

    protected fun makeToast(@StringRes messageRes: Int) {
        if (isThreadMain()) _toastResLiveData.value = messageRes
        else _toastResLiveData.postValue(messageRes)
    }
    protected fun makeToast(message: String) {
        if (isThreadMain()) _toastStrLiveData.value = message
        else _toastStrLiveData.postValue(message)
    }


    protected val _blockUiInteraction = SingleLiveEvent<Boolean>()
    fun blockUiInteraction(): LiveData<Boolean> = _blockUiInteraction

    /*
     * Ui State
     */
    protected fun showProgress() {
        getUiStateLiveData()?.value = UiState.Progress()
    }

    protected fun showError(message: String? = null) {
        getUiStateLiveData()?.value = UiState.Error(message)
    }


    protected fun showContentNotAvailable(message: String? = null) {
        getUiStateLiveData()?.value = UiState.NoContent(message)
    }

    protected fun showContent() {
        getUiStateLiveData()?.value = UiState.Content
    }

    /*
      Non-Blocking Progress
     */

    protected val _isProgress = SingleLiveEvent<Boolean>()
    fun isProgress(): LiveData<Boolean> = _isProgress


    protected open fun getUiStateLiveData(): MutableLiveData<UiState>? {
        return null
    }

}
