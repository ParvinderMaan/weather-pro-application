package com.jpmorgan.weather.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jpmorgan.commons.viewmodel.getViewModel
import com.jpmorgan.networks.weather.api.model.WeatherCity
import com.jpmorgan.misc.Navigator
import com.jpmorgan.weather.RepositoryInjection
import com.jpmorgan.weather.base.BaseAppFragment
import com.jpmorgan.weather.databinding.FragmentWeatherSearchBinding
import com.jpmorgan.weather.ui.detail.WeatherDetailViewModel
import timber.log.Timber


class WeatherSearchFragment : BaseAppFragment<FragmentWeatherSearchBinding>() {

    private val searchViewModel by lazy {
        getViewModel {
            WeatherSearchViewModel(RepositoryInjection.weatherRepository())
        }
    }

    private val weatherDetailViewModel by lazy {
        requireActivity().getViewModel {
            WeatherDetailViewModel(RepositoryInjection.weatherRepository(),RepositoryInjection.storageRepository(requireActivity()))
        }
    }



    override fun onViewCreated() {
        requireViewModelRegistrar().registerViewModels(searchViewModel,weatherDetailViewModel)
        initSearchView()
        initListeners()
        addObservers()
    }

    private fun initListeners() {
        getBinding().svWeatherContent.searchContentClicked { item ->
            weatherDetailViewModel.setGeoLocation(item.lat,item.lon)
            fragmentListener?.showFragment(Navigator.Fragment.Detail)
        }
    }

    private fun addObservers() {
        searchViewModel.searchCityResults().observe(viewLifecycleOwner) { lstOfSuggestions: List<WeatherCity> ->
            getBinding().svWeatherContent.setSearchContent(lstOfSuggestions)
        }
    }

    private fun initSearchView() {
        getBinding().svWeather.enable()
        getBinding().svWeather.showKeyboard()
        getBinding().svWeather.onSearchRequested { query, querySubmitted ->
            Timber.i("onSearchRequested ~~~~~> $query")

            if (query == null)
                return@onSearchRequested

            if (query.isBlank()) {
                searchViewModel.resetQuery()
                return@onSearchRequested
            }

            if (querySubmitted) {
                searchViewModel.setQuery(query)
            }
        }
    }


    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherSearchBinding {
        return FragmentWeatherSearchBinding.inflate(inflater, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeatherSearchFragment()
    }

}