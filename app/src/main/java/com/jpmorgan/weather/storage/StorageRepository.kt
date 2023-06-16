package com.jpmorgan.weather.storage

import com.jpmorgan.networks.weather.api.model.WeatherDetails
import kotlinx.coroutines.flow.Flow


class StorageRepository(
    private val storageDataSource: StorageDataSource,
) : StorageDataSource {

	override fun userLastCityWeather(): Flow<WeatherDetails?> =storageDataSource.userLastCityWeather()

	override suspend fun userLastCityWeather(weatherCity: WeatherDetails) {
		storageDataSource.userLastCityWeather(weatherCity)
	}

	override suspend fun clearCityWeather() {
		storageDataSource.clearCityWeather()
	}

}
