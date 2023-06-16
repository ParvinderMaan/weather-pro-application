package com.jpmorgan.weather.storage

import com.jpmorgan.networks.weather.api.model.WeatherDetails
import kotlinx.coroutines.flow.Flow

interface StorageDataSource {

    /*
     * Core
     */
    fun userLastCityWeather(): Flow<WeatherDetails?>
	suspend fun userLastCityWeather(weatherDetails: WeatherDetails)


	suspend fun clearCityWeather()
}
