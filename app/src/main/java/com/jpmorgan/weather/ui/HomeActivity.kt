package com.jpmorgan.weather.ui

import android.os.Bundle
import com.jpmorgan.misc.Navigator
import com.jpmorgan.weather.R
import com.jpmorgan.weather.base.BaseAppActivity

class HomeActivity : BaseAppActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            showFragment(Navigator.Fragment.Home)
            }
        }
 }

