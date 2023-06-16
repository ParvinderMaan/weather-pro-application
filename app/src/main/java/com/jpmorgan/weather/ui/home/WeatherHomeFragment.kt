package com.jpmorgan.weather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jpmorgan.commons.extentions.makeToast
import com.jpmorgan.commons.permissions.LocationPermissionManager
import com.jpmorgan.commons.viewmodel.getViewModel
import com.jpmorgan.misc.GeoLocationProvider
import com.jpmorgan.misc.Navigator
import com.jpmorgan.weather.R
import com.jpmorgan.weather.RepositoryInjection
import com.jpmorgan.weather.base.BaseAppFragment
import com.jpmorgan.weather.databinding.FragmentWeatherHomeBinding
import com.jpmorgan.weather.ui.detail.WeatherDetailViewModel

class WeatherHomeFragment : BaseAppFragment<FragmentWeatherHomeBinding>() {
    private lateinit var permissionManager: LocationPermissionManager

    companion object {
        @JvmStatic
        fun newInstance() = WeatherHomeFragment()
    }

    private val viewModel by lazy {
        getViewModel {
            WeatherHomeViewModel(RepositoryInjection.storageRepository(requireActivity().applicationContext),
                GeoLocationProvider(requireActivity())
            )
        }
    }

    private val weatherDetailViewModel by lazy {
        requireActivity().getViewModel {
            WeatherDetailViewModel(RepositoryInjection.weatherRepository(),RepositoryInjection.storageRepository(requireActivity()))
        }
    }


    override fun onViewCreated() {
        requireViewModelRegistrar().registerViewModels(viewModel,weatherDetailViewModel)
        getBinding().clWeather.hide()

        getBinding().svWeather.onSearchViewClick {
            fragmentListener?.showFragment(Navigator.Fragment.Search)
        }

        viewModel.weatherDetails().observe(viewLifecycleOwner) { weatherDetails ->
            getBinding().clWeather.setWeatherDetails(weatherDetails)
            getBinding().clWeather.show()
        }

        viewModel.geoCoordinates().observe(viewLifecycleOwner) { coordinates ->
            weatherDetailViewModel.setGeoLocation(coordinates.first,coordinates.second)
            fragmentListener?.showFragment(Navigator.Fragment.Detail)
        }

        getBinding().clLocality.localityOnClick {

            if (!permissionManager.hasPermission()) {
                permissionManager.requestPermission()
                return@localityOnClick
            }

            viewModel.fetchLocation()
        }


        viewModel.loadLastCityWeatherDetails()
    }



    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherHomeBinding {
        return FragmentWeatherHomeBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionManager= LocationPermissionManager(this, onPermissionGranted = {
            viewModel.fetchLocation()
        }, onPermissionDenied = {
            makeToast(getString(R.string.denied_permission))
        })
    }

}