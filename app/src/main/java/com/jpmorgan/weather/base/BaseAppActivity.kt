package com.jpmorgan.weather.base

import android.content.Context
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import com.jpmorgan.misc.Navigator
import com.jpmorgan.weather.R
import com.jpmorgan.weather.ui.detail.WeatherDetailFragment
import com.jpmorgan.weather.ui.home.WeatherHomeFragment
import com.jpmorgan.weather.ui.search.WeatherSearchFragment


abstract class BaseAppActivity : BaseActivity(), Navigator.FragmentSelectedListener {

    override fun attachBaseContext(newBase: Context) {
        val newConfig = Configuration(newBase.resources.configuration)
        newConfig.fontScale = 1.0f
        applyOverrideConfiguration(newConfig)
        super.attachBaseContext(newBase)
    }

    override fun showFragment(tag: Navigator.Fragment) {
        when (tag) {
            Navigator.Fragment.Home -> startFragment(WeatherHomeFragment.newInstance(), tag)
            Navigator.Fragment.Detail -> startFragment(WeatherDetailFragment.newInstance(), tag)
            Navigator.Fragment.Search -> startFragment(WeatherSearchFragment.newInstance(), tag)

        }
    }

    private fun startFragment(fragment: Fragment, tag: Navigator.Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragment_container_view, fragment)
                addToBackStack(tag.toString())
                commit()
            }
    }

    override fun popTillFragment(tag: String, flag: Int) {

    }

    override fun popTopMostFragment() {
        supportFragmentManager.popBackStack()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            popTopMostFragment()
        }
    }

}
