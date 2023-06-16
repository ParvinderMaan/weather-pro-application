package com.jpmorgan.networks.weather.source.remote.model.core


open class WeatherObjectResponse<T>(

	val data: T? = null

) : WeatherResponse()
