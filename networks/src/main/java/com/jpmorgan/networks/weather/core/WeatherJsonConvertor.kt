package com.jpmorgan.networks.weather.core

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.jpmorgan.networks.weather.api.model.WeatherCity

object WeatherJsonConvertor {

    inline fun <reified T> jsonObjectToClass(jsonObject: JsonObject): T {
        val gson = Gson()
        return gson.fromJson(jsonObject, T::class.java)
    }

    inline fun <reified T> jsonArrayToClass(jsonArray: JsonArray): List<T> {
        val gson = Gson()
        val itemType = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(jsonArray.toString(), itemType)
    }


    inline fun <reified T> objectToString(obj: T): String {
        val gson = Gson()
        return gson.toJson(obj)
    }

    inline fun <reified T> jsonStringToObject(jsonString: String): T? {
        val gson = Gson()
        return gson.fromJson(jsonString, T::class.java)
    }

}