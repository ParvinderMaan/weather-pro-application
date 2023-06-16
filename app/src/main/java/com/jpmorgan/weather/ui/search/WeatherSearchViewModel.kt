package com.jpmorgan.weather.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jpmorgan.commons.utils.SingleLiveEvent
import com.jpmorgan.commons.viewmodel.BaseViewModel
import com.jpmorgan.networks.weather.api.model.WeatherCity
import com.jpmorgan.networks.weather.api.model.WeatherCityGeocodeExterior
import com.jpmorgan.networks.weather.source.WeatherRepository
import com.jpmorgan.misc.Constant
import com.jpmorgan.weather.OpenWeather
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class WeatherSearchViewModel(private var repository: WeatherRepository) : BaseViewModel() {

    private val _searchCityResults = SingleLiveEvent<List<WeatherCity>>()
    fun searchCityResults(): LiveData<List<WeatherCity>> = _searchCityResults

    private val _queryStateFlow = MutableStateFlow(Constant.EMPTY)
    private fun queryStateFlow(): StateFlow<String> = _queryStateFlow

    private var searchCityJob: Job? = null

    init {

        viewModelScope.launch {
            queryStateFlow()
                .debounce(MIN_TYPE_INTERVAL)
                .collect { query ->
                    attemptSearch(query)
                }
        }
    }

    fun setQuery(query: String) {
        this._queryStateFlow.value = query
    }

    fun resetQuery() {
        setQuery(Constant.EMPTY)
        searchCityJob?.cancel()
        clearSuggestions()
    }

    private fun clearSuggestions() {
        _searchCityResults.value = emptyList()
    }

    private fun attemptSearch(query: String) {
        if (query.isBlank())
            return

        searchCityJob?.cancel()
        searchCityJob = viewModelScope.launch(searchExceptionHandler) {

            val result = repository.fetchCityGeocodes(
                query = query,
                appId = OpenWeather.APP_ID,
                limit = Constant.DEFAULT_LIMIT
            )

            if (result.isError()) {
                showError("Error: " + result.message)
                return@launch
            }

            val searchExterior: WeatherCityGeocodeExterior = result.requireItem()
            val searchCities= searchExterior.lstOfCities

            if (searchCities.isEmpty()) {
                makeToast(Constant.NO_RESULT)
                return@launch
            }

            _searchCityResults.value = searchCities
        }
    }

    fun retrySearch() {
        attemptSearch(this._queryStateFlow.value)
    }


    private val searchExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->


            var errorMessage = throwable.message
            if (throwable is UnknownHostException || throwable is SocketTimeoutException) {
                errorMessage = Constant.NO_INTERNET
            }

            errorMessage = errorMessage ?: Constant.UNUSUAL_ERROR
            makeToast("$errorMessage")
        }


    companion object {
        private const val MIN_TYPE_INTERVAL: Long = 700
    }
}