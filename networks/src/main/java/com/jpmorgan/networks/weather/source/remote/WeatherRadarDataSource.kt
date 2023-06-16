package com.jpmorgan.networks.weather.source.remote

import com.google.gson.JsonElement
import com.jpmorgan.networks.weather.api.WeatherDataSource
import com.jpmorgan.networks.weather.api.model.WeatherCityGeocodeExterior
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.networks.weather.core.UniversalResult
import retrofit2.Response


internal class WeatherRadarDataSource(
	private val client: WeatherClient,
	private val mapper: WeatherMapper,
) : WeatherDataSource {
    override suspend fun fetchCityGeocodes(
        query: String,
        appId: String,
        limit: String
    ): UniversalResult<WeatherCityGeocodeExterior> {
        val response: Response<JsonElement> =
            client.fetchCityGeocodes(query,appId,limit)
        return mapper.processCityGeocodesResponse(response)
    }

    override suspend fun fetchWeatherDetails(
        lat: String,
        lon: String,
        appId: String
    ): UniversalResult<WeatherDetails> {
        val response: Response<WeatherDetails> =
            client.fetchWeatherDetails(lat, lon, appId)
        return mapper.processWeatherDetails(response)
    }


}
