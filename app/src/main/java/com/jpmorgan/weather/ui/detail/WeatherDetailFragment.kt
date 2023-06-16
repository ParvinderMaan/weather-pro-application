package com.jpmorgan.weather.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jpmorgan.commons.UiState
import com.jpmorgan.commons.viewmodel.getViewModel
import com.jpmorgan.weather.RepositoryInjection
import com.jpmorgan.weather.base.BaseAppFragment
import com.jpmorgan.weather.databinding.FragmentWeatherDetailBinding


class WeatherDetailFragment : BaseAppFragment<FragmentWeatherDetailBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = WeatherDetailFragment()
    }

    private val viewModel by lazy {
        requireActivity().getViewModel {
            WeatherDetailViewModel(RepositoryInjection.weatherRepository(),RepositoryInjection.storageRepository(requireActivity()))
        }
    }




    override fun onViewCreated() {
        requireViewModelRegistrar().registerViewModels(viewModel)
        initListeners()
        addObservers()

        viewModel.fetchWeatherDetails()
        getBinding().clWeather.hide()
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWeatherDetailBinding {
        return FragmentWeatherDetailBinding.inflate(inflater, container, false)
    }

    private fun initListeners() {
        getBinding().includeWeatherToolbar.ibtnClose.setOnClickListener {
           fragmentListener?.popTopMostFragment()
        }

    }

    private fun addObservers() {
        viewModel.uiState().observe(viewLifecycleOwner) {
            showUiState(it)
        }

        viewModel.weatherDetails().observe(viewLifecycleOwner) { weatherDetails ->
            getBinding().clWeather.setWeatherDetails(weatherDetails)
            getBinding().clWeather.show()
        }

        getBinding().uiStateView.errorActionCallback {
            viewModel.fetchWeatherDetails()
        }
    }

    private fun showUiState(it: UiState?) {
        when (it) {
            UiState.Content -> getBinding().uiStateView.showContent()
            is UiState.Error -> getBinding().uiStateView.showError(it)
            is UiState.NoContent -> getBinding().uiStateView.showNoContent(it)
            is UiState.Progress -> getBinding().uiStateView.showProgress(it)
            null -> {}
        }
    }


}