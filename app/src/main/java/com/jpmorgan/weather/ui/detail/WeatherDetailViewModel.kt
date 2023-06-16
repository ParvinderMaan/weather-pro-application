package com.jpmorgan.weather.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jpmorgan.commons.UiState
import com.jpmorgan.commons.utils.SingleLiveEvent
import com.jpmorgan.commons.viewmodel.BaseViewModel
import com.jpmorgan.networks.weather.api.model.WeatherDetails
import com.jpmorgan.networks.weather.source.WeatherRepository
import com.jpmorgan.misc.Constant
import com.jpmorgan.weather.OpenWeather
import com.jpmorgan.weather.storage.InstantStorage
import com.jpmorgan.weather.storage.StorageRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class WeatherDetailViewModel(private var repository: WeatherRepository,var storageRepository: StorageRepository) : BaseViewModel()  {
    private val _uiStateLiveData = MutableLiveData<UiState>()
    fun uiState(): LiveData<UiState> = _uiStateLiveData
    override fun getUiStateLiveData(): MutableLiveData<UiState> {
        return _uiStateLiveData
    }

    private val _weatherDetails = SingleLiveEvent<WeatherDetails>()
    fun weatherDetails(): LiveData<WeatherDetails> = _weatherDetails

    private lateinit var lat:String
    private lateinit var lon:String

    fun fetchWeatherDetails() {
        viewModelScope.launch(weatherExceptionHandler) {
            showProgress()
            val result = repository.fetchWeatherDetails(
                lat =lat,
                lon = lon,
                appId = OpenWeather.APP_ID
            )

            if (result.isError()) {
                showError(result.message)
                return@launch
            }

            val weatherDetails = result.requireItem()
            _weatherDetails.value = weatherDetails
            showContent()

            InstantStorage.saveCityWeather(storageRepository,weatherDetails)
        }
    }

    fun setGeoLocation(lat:String?, lon:String?){
        if(lat!=null) this.lat=lat
        if(lon!=null) this.lon=lon
    }


    private val weatherExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->

            var errorMessage = throwable.message
            if (throwable is UnknownHostException || throwable is SocketTimeoutException) {
                errorMessage = Constant.NO_INTERNET
            }

            errorMessage = errorMessage ?: Constant.UNUSUAL_ERROR
            showError("$errorMessage")
        }
}