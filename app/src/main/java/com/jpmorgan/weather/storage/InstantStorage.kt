package com.jpmorgan.weather.storage

import com.jpmorgan.networks.weather.api.model.WeatherDetails

object InstantStorage {

     suspend fun saveCityWeather(repository: StorageRepository, input: WeatherDetails) {
         repository.apply {
             userLastCityWeather(input)
         }

    }

    suspend fun removeCityWeather(repository: StorageRepository) {
        repository.clearCityWeather()
    }



}