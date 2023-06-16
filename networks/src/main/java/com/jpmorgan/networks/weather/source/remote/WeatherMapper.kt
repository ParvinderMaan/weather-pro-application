package com.jpmorgan.networks.weather.source.remote

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.jpmorgan.networks.weather.api.model.WeatherCity
import com.jpmorgan.networks.weather.api.model.WeatherCityGeocodeExterior
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.networks.weather.core.UniversalResult
import com.jpmorgan.networks.weather.core.WeatherJsonConvertor
import com.jpmorgan.networks.weather.source.remote.model.core.WeatherListResponse
import com.jpmorgan.networks.weather.source.remote.model.core.WeatherObjectResponse
import com.jpmorgan.networks.weather.source.remote.model.core.WeatherResponse
import retrofit2.Response

class WeatherMapper {
    companion object {

        private val VALID_SESSION_EXPIRY_RESPONSES = listOf(
            "Access denied",
            "Please enter correct user id.",
            "Please provide a User ID.",
            "Invalid login token",
            "Expired login token"
        )

        private const val SERVER_ERROR_MESSAGE =
            "Radar Server has encountered an issue. \nWe are working on a fix, please try again later."

        private const val SESSION_EXPIRED_MESSAGE =
            "You need to be a registered user to access this content. " +
                    "Please register a new account or login if you already have an account."
    }


    fun <ITEM : WeatherListResponse<*>> processListResponseAsWhole(
        response: Response<ITEM>
    ): UniversalResult<ITEM> {

        val universalResult = UniversalResult<ITEM>(
            response.code(),
            response.message()
        )

        if (!response.isSuccessful) {

            universalResult.message = if (response.code() == 500) {
                SERVER_ERROR_MESSAGE
            } else {
                "API Error ${response.code()} : ${response.message()}"
            }
            return universalResult
        }

        val item = response.body()
        if (item == null) {
            universalResult.message = "Empty Response : ${response.code()} : ${response.message()}"
            return universalResult
        }

        if (!item.isSuccess()) {
            universalResult.setManualError()
            universalResult.message = item.message ?: ""

            if (VALID_SESSION_EXPIRY_RESPONSES.contains(universalResult.message)) {
                universalResult.code = 401
                universalResult.message = SESSION_EXPIRED_MESSAGE
            }

            return universalResult
        }

        universalResult.message = item.message ?: ""
        universalResult.item = item

        return universalResult
    }

    fun <ITEM> processListResponse(
        response: Response<WeatherListResponse<ITEM>>
    ): UniversalResult<ITEM> {

        val universalResult = UniversalResult<ITEM>(
            response.code(),
            response.message()
        )

        if (!response.isSuccessful) {

            universalResult.message = if (response.code() == 500) {
                SERVER_ERROR_MESSAGE
            } else {
                "API Error ${response.code()} : ${response.message()}"
            }
            return universalResult
        }

        val listResponse: WeatherListResponse<ITEM>? = response.body()
        if (listResponse == null) {
            universalResult.message = "Empty Response : ${response.code()} : ${response.message()}"
            return universalResult
        }

        if (!listResponse.isSuccess()) {
            universalResult.setManualError()
            universalResult.message = listResponse.message ?: ""

            if (VALID_SESSION_EXPIRY_RESPONSES.contains(universalResult.message)) {
                universalResult.code = 401
                universalResult.message = SESSION_EXPIRED_MESSAGE
            }

            return universalResult
        }

        universalResult.message = listResponse.message ?: ""
        universalResult.items = listResponse.data
        return universalResult
    }

    fun <ITEM> processObjectResponse(
        response: Response<WeatherObjectResponse<ITEM>>
    ): UniversalResult<ITEM> {

        val universalResult = UniversalResult<ITEM>(
            response.code(),
            response.message()
        )

        if (!response.isSuccessful) {

            universalResult.message = if (response.code() == 500) {
                SERVER_ERROR_MESSAGE
            } else {
                "API Error ${response.code()} : ${response.message()}"
            }
            return universalResult
        }

        val objectResponse: WeatherObjectResponse<ITEM>? = response.body()
        if (objectResponse == null) {
            universalResult.message = "Empty Response : ${response.code()} : ${response.message()}"
            return universalResult
        }

        if (!objectResponse.isSuccess()) {
            universalResult.setManualError()
            universalResult.message = objectResponse.message ?: ""

            if (VALID_SESSION_EXPIRY_RESPONSES.contains(universalResult.message)) {
                universalResult.code = 401
                universalResult.message = SESSION_EXPIRED_MESSAGE
            }

            return universalResult
        }

        universalResult.message = objectResponse.message ?: ""
        universalResult.item = objectResponse.data
        return universalResult
    }

    fun processResponse(
        response: Response<WeatherResponse>
    ): UniversalResult<Any> {

        val universalResult = UniversalResult<Any>(
            response.code(),
            response.message()
        )

        if (!response.isSuccessful) {

            universalResult.message = if (response.code() == 500) {
                SERVER_ERROR_MESSAGE
            } else {
                "API Error ${response.code()} : ${response.message()}"
            }
            return universalResult
        }

        val messageResponse: WeatherResponse? = response.body()
        if (messageResponse == null) {
            universalResult.message = "Empty Response : ${response.code()} : ${response.message()}"
            return universalResult
        }

        if (!messageResponse.isSuccess()) {
            universalResult.setManualError()
            universalResult.message = messageResponse.message ?: ""

            if (VALID_SESSION_EXPIRY_RESPONSES.contains(universalResult.message)) {
                universalResult.code = 401
                universalResult.message = SESSION_EXPIRED_MESSAGE
            }

            return universalResult
        }

        universalResult.message = messageResponse.message ?: ""
        return universalResult
    }

    fun  processCityGeocodesResponse(response: Response<JsonElement>): UniversalResult<WeatherCityGeocodeExterior> {
        val universalResult = UniversalResult<WeatherCityGeocodeExterior>(
            response.code(),
            response.message()
        )

        if (!response.isSuccessful) {

            universalResult.message = if (response.code() == 500) {
                SERVER_ERROR_MESSAGE
            } else {
                "API Error ${response.code()} : ${response.message()}"
            }
            return universalResult
        }

        val responseBody: JsonElement? = response.body()

        if (responseBody == null) {
            universalResult.message = "Empty Response : ${response.code()} : ${response.message()}"
            return universalResult
        }


        if (responseBody.isJsonObject) {
            val jsonObject = responseBody.asJsonObject
            val weatherResponse:WeatherResponse= WeatherJsonConvertor.jsonObjectToClass(jsonObject)
            universalResult.message = weatherResponse.message ?: ""
            universalResult.code = weatherResponse.responseCode.toInt()

        } else if (responseBody.isJsonArray) {
            val jsonArray: JsonArray = responseBody.asJsonArray
            val listOfCity: List<WeatherCity> = WeatherJsonConvertor.jsonArrayToClass(jsonArray)
            val weather=WeatherCityGeocodeExterior(listOfCity)
            universalResult.item =weather
        }

        return universalResult

    }

    fun processWeatherDetails(response: Response<WeatherDetails>): UniversalResult<WeatherDetails> {
        val universalResult = UniversalResult<WeatherDetails>(
            response.code(),
            response.message()
        )

        if (!response.isSuccessful) {

            universalResult.message = if (response.code() == 500) {
                SERVER_ERROR_MESSAGE
            } else {
                "API Error ${response.code()} : ${response.message()}"
            }
            return universalResult
        }

        val messageResponse: WeatherDetails? = response.body()
        if (messageResponse == null) {
            universalResult.message = "Empty Response : ${response.code()} : ${response.message()}"
            return universalResult
        }

        if (!messageResponse.isSuccess()) {
            universalResult.setManualError()
            universalResult.message = ""
            return universalResult

        }

        universalResult.item=messageResponse
        return universalResult
    }

}