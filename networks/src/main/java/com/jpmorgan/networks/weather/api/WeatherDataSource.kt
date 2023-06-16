package com.jpmorgan.networks.weather.api

import com.jpmorgan.networks.weather.api.model.WeatherCityGeocodeExterior
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.networks.weather.core.UniversalResult

interface WeatherDataSource {

    suspend fun fetchCityGeocodes(
        query: String,
        appId: String,
        limit: String
    ): UniversalResult<WeatherCityGeocodeExterior>

    suspend fun fetchWeatherDetails(
        lat:String,
        lon:String,
        appId:String
    ): UniversalResult<WeatherDetails>
}