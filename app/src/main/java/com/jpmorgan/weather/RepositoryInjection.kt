package com.jpmorgan.weather
import android.content.Context
import com.jpmorgan.networks.NetworkProxy
import com.jpmorgan.networks.weather.source.WeatherRepository
import com.jpmorgan.weather.storage.StorageRepository
import com.jpmorgan.weather.storage.WeatherStorageDataSource

// TODO: export for debug/release
object RepositoryInjection {

    private lateinit var weatherRepositoryInstance: WeatherRepository
    private lateinit var storageRepositoryInstance: StorageRepository



    fun storageRepository (context: Context) : StorageRepository {

        if (!::storageRepositoryInstance.isInitialized) {
            storageRepositoryInstance = StorageRepository(
                WeatherStorageDataSource(context.applicationContext)
            )
        }

        return storageRepositoryInstance
    }

    fun weatherRepository(): WeatherRepository {

        if (!::weatherRepositoryInstance.isInitialized) {
            weatherRepositoryInstance = WeatherRepository(NetworkProxy.fromWeather())

        }


        return weatherRepositoryInstance
    }
}
