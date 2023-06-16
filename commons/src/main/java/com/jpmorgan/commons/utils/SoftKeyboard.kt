package com.jpmorgan.commons.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object SoftKeyboard {

    private fun getInputMethodManager(context: Context): InputMethodManager? {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    }

    fun show(editText: EditText) {
        editText.postDelayed({
            editText.requestFocus()
            getInputMethodManager(editText.context)?.showSoftInput(
                editText, InputMethodManager.SHOW_IMPLICIT
            )
        }, 100)
    }

    fun hide(editText: EditText) {
        getInputMethodManager(editText.context)?.hideSoftInputFromWindow(
            editText.windowToken, 0
        )
    }

    fun hide(activity: Activity?) {

        if (activity == null) {
            return
        }

        getInputMethodManager(activity)?.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }
}
