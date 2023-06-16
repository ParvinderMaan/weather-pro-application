package com.jpmorgan.networks.weather.source.remote.model.core


open class WeatherListResponse<T>(
	val data: List<T>? = null,

) : WeatherResponse()
