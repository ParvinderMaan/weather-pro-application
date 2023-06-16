package com.jpmorgan.weather.base

interface BindingAware<BINDING> {

    fun hasBinding(): Boolean

    var shouldDestroyBinding: Boolean
}