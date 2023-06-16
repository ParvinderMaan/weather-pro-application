package com.jpmorgan.networks.weather.source.remote.model.core

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class WeatherResponse {

	@SerializedName("cod")
	@Expose
	val responseCode: String = "0"

	@SerializedName("message")
	@Expose
	val message: String? = null

	fun isSuccess () : Boolean {
		return responseCode=="200"
	}
}
