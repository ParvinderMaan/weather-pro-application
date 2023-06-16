package com.jpmorgan.weather.base


import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.jpmorgan.commons.viewmodel.BaseViewModel
import com.jpmorgan.commons.viewmodel.ViewModelRegistrar


abstract class BaseActivity : AppCompatActivity(), ViewModelRegistrar{

    override fun onBackPressed() {
        finish()
    }

    protected fun isStartedForResult(): Boolean {
        return (callingActivity != null)
    }


    /*
     * Initialization [BaseViewModel]
     */

    override fun registerViewModels(vararg viewModels: ViewModel) {
        viewModels.forEach { vieModel -> registerViewModel(vieModel) }
    }

    private fun registerViewModel(viewModel: ViewModel) {
        if (viewModel is LifecycleObserver) {
            lifecycle.addObserver(viewModel)
        }

        if (viewModel is BaseViewModel) {
            registerViewModel(viewModel)
        }
    }

    private fun registerViewModel(viewModel: BaseViewModel) {

        viewModel.onShowToastResource().observe(this) { textRes ->
            showToast(this.getString(textRes))
        }

        viewModel.onShowToastNonResource().observe(this) { textStr ->
            showToast(textStr)
        }

    }
    protected fun showToast(text: String) {
        Toast.makeText(applicationContext,text,Toast.LENGTH_SHORT).show()
    }





}
