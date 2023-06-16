package com.jpmorgan.networks.weather.source

import com.jpmorgan.networks.weather.api.WeatherDataSource
import com.jpmorgan.networks.weather.api.model.WeatherCityGeocodeExterior
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.networks.weather.core.UniversalResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepository(private val dataSource: WeatherDataSource):WeatherDataSource {

    override suspend fun fetchCityGeocodes(
        query: String,
        appId: String,
        limit: String
    ): UniversalResult<WeatherCityGeocodeExterior> =
        withContext(Dispatchers.IO) {
            dataSource.fetchCityGeocodes(query,appId,limit)
        }



    override suspend fun fetchWeatherDetails(
        lat: String,
        lon: String,
        appId: String
    ): UniversalResult<WeatherDetails> =
        withContext(Dispatchers.IO) {
            dataSource.fetchWeatherDetails(lat,lon,appId)
        }



}