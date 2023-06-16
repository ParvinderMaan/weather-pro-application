package com.jpmorgan.misc

import android.content.Context
import androidx.core.content.ContextCompat
import com.jpmorgan.weather.R

class ColorProvider constructor(context: Context) {

    val red by lazy {
        ContextCompat.getColor(
            context,
            R.color.red
        )
    }

    val purple by lazy {
        ContextCompat.getColor(
            context,
            R.color.purple
        )
    }

    val greyLight by lazy {
        ContextCompat.getColor(
            context,
            R.color.grey_light
        )
    }

    val white by lazy {
        ContextCompat.getColor(
            context,
            R.color.white
        )
    }

    val green by lazy {
        ContextCompat.getColor(
            context,
            R.color.green
        )
    }

    val whiteHoneydew by lazy {
        ContextCompat.getColor(
            context,
            R.color.white_honeydew
        )
    }

    val whiteMagnolia by lazy {
        ContextCompat.getColor(
            context,
            R.color.white_magnolia
        )
    }

    val whiteGhost by lazy {
        ContextCompat.getColor(
            context,
            R.color.white_ghost
        )
    }

    val yellowCorn by lazy {
        ContextCompat.getColor(
            context,
            R.color.yellow_corn
        )
    }

    val redChablis by lazy {
        ContextCompat.getColor(
            context,
            R.color.red_chablis
        )
    }

    val blueAlice by lazy {
        ContextCompat.getColor(
            context,
            R.color.blue_alice
        )
    }

    val green_ by lazy {
        ContextCompat.getColor(
            context,
            R.color.green_
        )
    }

    val purple_ by lazy {
        ContextCompat.getColor(
            context,
            R.color.purple_
        )
    }

    val blue_ by lazy {
        ContextCompat.getColor(
            context,
            R.color.blue_
        )
    }

    val yellow_ by lazy {
        ContextCompat.getColor(
            context,
            R.color.yellow_
        )
    }

    val red_ by lazy {
        ContextCompat.getColor(
            context,
            R.color.red_
        )
    }

    val blue by lazy {
        ContextCompat.getColor(
            context,
            R.color.blue
        )
    }

    val black by lazy {
        ContextCompat.getColor(
            context,
            R.color.black
        )
    }

}