package com.jpmorgan.commons.viewmodel

import androidx.lifecycle.ViewModel

interface ViewModelRegistrar {
	fun registerViewModels(vararg viewModels: ViewModel)
}
