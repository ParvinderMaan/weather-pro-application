package com.jpmorgan.weather.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.jpmorgan.commons.UiState
import com.jpmorgan.weather.R
import com.jpmorgan.weather.databinding.ViewUiStateBinding


class UiStateView : ConstraintLayout {
    private var _binding: ViewUiStateBinding? = null
    private val binder get() = _binding!!

    private var errorActionCallback: (() -> Unit)? = null
    private var errorActionSessionLapseCallback: (() -> Unit)? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        _binding=ViewUiStateBinding.inflate(LayoutInflater.from(context), this)
    }
    override fun onFinishInflate() {
        super.onFinishInflate()
        initialize()
    }

    private fun initialize() {

        showProgressView(false)
        showErrorView(false)
        showNoContentView(false)
        binder.buttonError.setOnClickListener {
            this.errorActionCallback?.invoke()
        }

    }

    private fun showErrorView(value: Boolean) {
        binder.groupError.isVisible = value
    }

    private fun showNoContentView(value: Boolean) {
        binder.textViewNoContent.isVisible = value
    }

    private fun showProgressView(value: Boolean) {
        binder.progressBar.isVisible = value
    }


    fun showProgress(progress: UiState) {
        showProgressView(true)
        showErrorView(false)
        showNoContentView(false)
    }


    fun hideProgress() {
        binder.progressBar.isVisible = false
    }


    fun showContent() {
        showProgressView(false)
        showErrorView(false)
        showNoContentView(false)
    }

    fun showError(uiState: UiState.Error) {
        showProgressView(false)
        showErrorView(true)
        showNoContentView(false)
        setErrorText(uiState)
        setErrorAction(uiState)
        setErrorActionText(uiState)
    }

    private fun setErrorAction(error: UiState.Error) {
        binder.buttonError.isVisible = error.action

        binder.buttonError.setOnClickListener {
            this.errorActionCallback?.invoke()
        }

    }

    private fun setErrorActionText(error: UiState.Error) {
        val errorActionText =binder.buttonError.text.toString()
        binder.buttonError.text = errorActionText
    }

    private fun setErrorText(error: UiState.Error) {
        binder.textViewError.text =  error.msg
    }


    fun errorActionCallback(action: () -> Unit) {
        this.errorActionCallback = action
    }

    fun errorSessionLapseCallback(action: () -> Unit) {
        this.errorActionSessionLapseCallback = action
    }

    fun showNoContent(it: UiState.NoContent) {
        showProgressView(false)
        showErrorView(false)
        showNoContentView(true)
        showNoContentMsg(it)
    }

    private fun showNoContentMsg(it: UiState.NoContent) {
        if (it.msg != null) binder.textViewNoContent.text = it.msg
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

}