package com.jpmorgan.weather.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.jpmorgan.commons.extentions.clearText
import com.jpmorgan.commons.utils.OnSingleClickListener
import com.jpmorgan.commons.utils.SoftKeyboard
import com.jpmorgan.misc.ColorProvider
import com.jpmorgan.weather.databinding.ViewWeatherSearchBinding

class WeatherSearchView(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {


    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    private val binder by lazy {
        ViewWeatherSearchBinding.inflate(layoutInflater, this)
    }

    private val colorProvider by lazy {
        ColorProvider(this.context)
    }

    private  var searchViewClick: (() -> Unit)? = null
    fun onSearchViewClick(searchViewClick: ()-> Unit){
        this.searchViewClick=searchViewClick
    }

    private  var onSearchRequested: ((query: String?, querySubmitted: Boolean ) -> Unit)? = null
    fun onSearchRequested(onSearchRequested: (query: String?, querySubmitted: Boolean)-> Unit){
        this.onSearchRequested=onSearchRequested
    }
    override fun onFinishInflate() {
        super.onFinishInflate()

        binder.ivCancel.setOnClickListener {
            clearText()
            onSearchRequested(query = null,querySubmitted = true)
        }


        binder.edtSearch.addTextChangedListener { editable ->
            onSearchQueryChanged(editable.toString().trim())
        }

        binder.edtSearch.setOnEditorActionListener(
            object : TextView.OnEditorActionListener {

                override fun onEditorAction(
                    v: TextView?,
                    actionId: Int,
                    event: KeyEvent?,
                ): Boolean {

                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        onSearchRequested(v?.text.toString(),querySubmitted = false)
                        return true
                    }

                    return false
                }
            })


        binder.viewGlass.setOnClickListener(object: OnSingleClickListener() {
            override fun onSingleClick(v: View?) {
                searchViewClick?.invoke()
            }
        })

    }

    fun setSearchText(text:String) {
        binder.edtSearch.setText(text)
        binder.edtSearch.setSelection(binder.edtSearch.length())
    }

    fun enable() {
       binder.viewGlass.isVisible=false
    }

    fun disable() {
        binder.viewGlass.isVisible=true
    }

    fun setSearchCancelable(bool:Boolean) {
        binder.ivCancel.isVisible=bool
    }

    fun setHint(hint:String) {
        binder.edtSearch.hint = hint
    }


    private fun onSearchQueryChanged(query: String) {
        binder.ivCancel.visibility =
            if (query.isNotBlank()) View.VISIBLE else View.GONE

        onSearchRequested(query = query, querySubmitted = true, closeKeyboard = false)
    }

    private fun onSearchRequested(
        query: String?,
        querySubmitted: Boolean = false,
        closeKeyboard: Boolean = true
    ) {

        if (closeKeyboard) {
            hideKeyboard()
        }

        onSearchRequested?.invoke(query, querySubmitted)
    }

    private fun clearText() {
        binder.edtSearch.clearText()
    }

    fun clearInputFocus() {
        binder.edtSearch.clearFocus()
    }

    fun showKeyboard() {
        SoftKeyboard.show(binder.edtSearch)
    }

    private fun hideKeyboard() {
        SoftKeyboard.hide(binder.edtSearch)
    }

}