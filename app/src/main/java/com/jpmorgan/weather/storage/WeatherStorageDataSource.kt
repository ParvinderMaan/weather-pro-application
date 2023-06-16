package com.jpmorgan.weather.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.jpmorgan.networks.weather.api.model.WeatherCity
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.networks.weather.core.WeatherJsonConvertor
import com.jpmorgan.weather.storage.WeatherStorageDataSource.Companion.DATA_STORE_NAME
import kotlinx.coroutines.flow.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

class WeatherStorageDataSource(
    var context: Context,
) : StorageDataSource {

    companion object {
        const val DATA_STORE_NAME = "storage"
        const val KEY_USER_CITY = "key_user_city"

    }

    override fun userLastCityWeather(): Flow<WeatherDetails?> {
        return context.dataStore.data.map { preferences: Preferences ->
            preferences[stringPreferencesKey(KEY_USER_CITY)]
        }.map {
            WeatherJsonConvertor.jsonStringToObject(it?:"")
        }
    }

    override suspend fun userLastCityWeather(weatherCity: WeatherDetails) {
        val weather = WeatherJsonConvertor.objectToString(weatherCity)
        context.dataStore.edit { preferences ->
            preferences[stringPreferencesKey(KEY_USER_CITY)] = weather
        }
    }

    override suspend fun clearCityWeather() {
        context.dataStore.edit { it.clear() }
    }

}
