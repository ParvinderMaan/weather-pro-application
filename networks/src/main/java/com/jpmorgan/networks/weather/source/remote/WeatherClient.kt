package com.jpmorgan.networks.weather.source.remote

import com.google.gson.JsonElement
import com.jpmorgan.networks.weather.WeatherUrl
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.networks.weather.source.remote.model.core.WeatherObjectResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherClient {


    @GET(WeatherUrl.CITY_GEOCODE)
    suspend fun fetchCityGeocodes(
        @Query("q") query:String,
        @Query("appid") appId:String,
        @Query("limit") limit:String,
    ): Response<JsonElement>


    @GET(WeatherUrl.WEATHER_DETAILS)
    suspend fun fetchWeatherDetails(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("appid") appId:String,
    ): Response<WeatherDetails>
}