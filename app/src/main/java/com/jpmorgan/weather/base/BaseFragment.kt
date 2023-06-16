package com.jpmorgan.weather.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.jpmorgan.commons.viewmodel.ViewModelRegistrar

abstract class BaseFragment : Fragment() {

	private var viewModelRegistrar: ViewModelRegistrar? = null

	override fun onAttach(context: Context) {
		super.onAttach(context)
		viewModelRegistrar = context as? ViewModelRegistrar
	}

	fun getViewModelRegistrar(): ViewModelRegistrar? {
		return viewModelRegistrar
	}

	fun requireViewModelRegistrar(): ViewModelRegistrar {
		return requireNotNull(viewModelRegistrar)
	}
}
