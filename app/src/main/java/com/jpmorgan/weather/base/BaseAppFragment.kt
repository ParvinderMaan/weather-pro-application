package com.jpmorgan.weather.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.jpmorgan.misc.Navigator
import timber.log.Timber

abstract class BaseAppFragment<BINDING : ViewBinding> : BaseFragment(), BindingAware<BINDING>{

	private var binding: BINDING? = null

	abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?, ): BINDING

	 var fragmentListener: Navigator.FragmentSelectedListener? = null

	open fun onObserversRequested() {}

	open fun onViewCreated() {}



	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View? {

		if (hasBinding()) {

			val rootView = getBinding().root
			Timber.d("onCreateView: Already has binding; root-parent=${rootView.parent}")

			val viewGroup = rootView.parent as? ViewGroup
			if (viewGroup != null) {
				viewGroup.removeView(rootView)
				Timber.d("onCreateView: Already had binding; removed-parent; root-parent=${rootView.parent}")
			}

			return rootView
		}

		binding = createBinding(inflater, container)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		onObserversRequested()
		onViewCreated()
	}

	override fun onDestroy() {
		if (shouldDestroyBinding) {
			binding = null
		}

		super.onDestroy()
	}


	inline fun safeContext(action: (context: Context) -> Unit) {

		val context = context
		if (context != null) {
			action(context)
		} else {
			Timber.e("safeContext() prevented a null-context call.")
		}
	}

	inline fun safeActivity(action: (activity: FragmentActivity) -> Unit) {

		val activity = activity
		if (activity != null) {
			action(activity)
		} else {
			Timber.e("safeActivity() prevented a null-activity call.")
		}
	}

	inline fun safeBinding(action: (binding: BINDING) -> Unit) {
		if (hasBinding()) {
			action(getBinding())
		} else {
			Timber.e("safeBinding() prevented a null-binding call.")
		}
	}

	/*
	 * [BindingAware]
	 */

	override var shouldDestroyBinding: Boolean = false

	override fun hasBinding(): Boolean {
		return binding != null
	}

	fun getBinding(): BINDING = binding ?: throw NullPointerException("Binding is null.")

	override fun onAttach(context: Context) {
		super.onAttach(context)
		fragmentListener = context as Navigator.FragmentSelectedListener
	}
}
