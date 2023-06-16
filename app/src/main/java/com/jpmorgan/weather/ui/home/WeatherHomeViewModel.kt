package com.jpmorgan.weather.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jpmorgan.commons.utils.SingleLiveEvent
import com.jpmorgan.commons.viewmodel.BaseViewModel
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.misc.Constant
import com.jpmorgan.misc.GeoLocationProvider
import com.jpmorgan.weather.R
import com.jpmorgan.weather.storage.StorageRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class WeatherHomeViewModel(
    private var storageRepository: StorageRepository,
    private var locationProvider: GeoLocationProvider
) : BaseViewModel()  {

    private val _weatherDetails = SingleLiveEvent<WeatherDetails>()
    fun weatherDetails(): LiveData<WeatherDetails> = _weatherDetails

    private val _geoCoordinates = SingleLiveEvent<Pair<String,String>>()
    fun geoCoordinates(): LiveData<Pair<String,String>> = _geoCoordinates

     fun loadLastCityWeatherDetails() {
        viewModelScope.launch(weatherExceptionHandler) {
            storageRepository.userLastCityWeather()
                .flowOn(Dispatchers.IO)
                .collect { weatherDetails->
                    if(weatherDetails!=null){
                        //show
                        _weatherDetails.postValue(weatherDetails)
                    }else{
                        //hide
                    }
                }
        }

    }

    fun fetchLocation() {
        locationProvider.fetchLocation { location: Location? ->
            if (location != null) {
                val latitude = location.latitude.toString()
                val longitude = location.longitude.toString()
                _geoCoordinates.value= Pair(latitude,longitude)
            } else {
                makeToast(R.string.try_some_other_time)
            }
        }
    }

    private val weatherExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->

            var errorMessage = throwable.message
            if (throwable is UnknownHostException || throwable is SocketTimeoutException) {
                errorMessage = Constant.NO_INTERNET
            }

            errorMessage = errorMessage ?: Constant.UNUSUAL_ERROR
            makeToast("$errorMessage")
        }
}