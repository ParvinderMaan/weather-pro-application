package com.jpmorgan.networks

import com.jpmorgan.networks.weather.api.WeatherDataSource
import com.jpmorgan.networks.weather.core.ServiceGenerator
import com.jpmorgan.networks.weather.source.WeatherRepository
import com.jpmorgan.networks.weather.source.remote.WeatherMapper
import com.jpmorgan.networks.weather.source.remote.WeatherRadarDataSource


object NetworkProxy {

    private val serviceGenerator = ServiceGenerator()
    private val weatherDataSource by lazy {
        WeatherRepository(
            WeatherRadarDataSource(
                serviceGenerator.getWeatherClient(),
                WeatherMapper()
            )
        )
    }

    fun fromWeather(): WeatherDataSource {
        return weatherDataSource
    }
}
