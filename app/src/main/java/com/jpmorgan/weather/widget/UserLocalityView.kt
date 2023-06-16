package com.jpmorgan.weather.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.jpmorgan.commons.utils.OnSingleClickListener
import com.jpmorgan.weather.databinding.ViewUserLocalityBinding


class UserLocalityView(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {

    private var localityOnClick: (() -> Unit)? = null

    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    private val binder by lazy {
        ViewUserLocalityBinding.inflate(layoutInflater, this)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        binder.clLocality.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View?) {
                       localityOnClick?.invoke()
            }

        })
    }



    fun show() {
        binder.clLocality.isVisible=true
    }

    fun hide() {
        binder.clLocality.isVisible=false
    }


    fun localityOnClick(action: () -> Unit) {
        this.localityOnClick = action
    }


}