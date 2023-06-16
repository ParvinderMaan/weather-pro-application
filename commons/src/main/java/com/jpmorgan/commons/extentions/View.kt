package com.jpmorgan.commons.extentions

import android.app.Activity
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment


/**
 * Properties
 */
var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

var View.isInvisible: Boolean
    get() = visibility == View.INVISIBLE
    set(value) {
        visibility = if (value) View.INVISIBLE else View.VISIBLE
    }

/**
 * Methods
 */
fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun EditText.clearText() {
    setText("")
}

fun Activity.makeToast(msg: String, duration: Int? = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration!!).show()
}

fun Fragment.makeToast(msg: String, duration: Int? = Toast.LENGTH_SHORT) {
    Toast.makeText(requireActivity(), msg, duration!!).show()
}

//TODO  remove unused methods if any !!!

