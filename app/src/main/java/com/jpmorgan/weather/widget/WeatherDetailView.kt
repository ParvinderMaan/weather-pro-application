package com.jpmorgan.weather.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import coil.load
import com.jpmorgan.networks.weather.WeatherUrl
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.misc.ColorProvider
import com.jpmorgan.weather.databinding.ViewWeatherDetailBinding

class WeatherDetailView(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {


    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    private val binder by lazy {
        ViewWeatherDetailBinding.inflate(layoutInflater, this)
    }

    private val colorProvider by lazy {
        ColorProvider(this.context)
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        binder
    }

    fun setWeatherDetails(text: WeatherDetails) {
        val temperature =text.main?.temp?.toString()?:""
        val feelsLike =text.main?.feelsLike?.toString()?:""
        val main= text.weather?.get(0)?.main?:""
        val cityName=text.name?:""
        //icon
        val icon =text.weather?.get(0)?.icon?:""
        val url="${IMAGE_URL}img/wn/$icon@2x.png"
        binder.ivWeatherIcon.loadImage(url)

        val humidity =text.main?.humidity?.toString()?:""
        val pressure =text.main?.pressure?.toString()?:""
        val speed=text.wind?.speed?.toString()?:""

        val mTemperature="$temperature${Symbol.degreeSymbol}"
        binder.tvTemperature.text = mTemperature

        val mFeelsLike="$feelsLike${Symbol.degreeSymbol}"
        binder.tvFeelLike.text = mFeelsLike

        binder.tvMain.text = main
        binder.tvCityName.text = cityName

        val mHumanity="$humidity${Symbol.percentageSymbol}"
        binder.tvHumidity.text = mHumanity

        val mSpeed= "$speed${Symbol.speedUnit}"
        binder.tvSpeed.text =mSpeed

        val mPressure= "$pressure${Symbol.percentageSymbol}"
        binder.tvPressure.text = mPressure
    }

    fun show() {
        binder.clSearch.isVisible=true
    }

    fun hide() {
        binder.clSearch.isVisible=false
    }

    private fun ImageView.loadImage(url:String?){
        this.load(url) {
            setHeader(WeatherUrl.Agent.KEY_AGENT, WeatherUrl.Agent.VALUE_AGENT)
            crossfade(true)
            crossfade(500)
        }
    }


   private object Symbol{
       const val degreeSymbol = "\u00B0"
       const val percentageSymbol = "%"
       const val speedUnit = "km/h"
   }

    companion object {
        const val IMAGE_URL="https://openweathermap.org/"
    }

}