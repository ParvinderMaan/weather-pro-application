package com.jpmorgan.weather.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jpmorgan.networks.weather.api.model.WeatherCity
import com.jpmorgan.weather.databinding.ListItemSearchBinding
import com.jpmorgan.weather.databinding.ViewWeatherSearchContentBinding

class WeatherSearchContentView(
    context: Context,
    attributeSet: AttributeSet
) : ConstraintLayout(context, attributeSet) {


    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    private val binder by lazy {
        ViewWeatherSearchContentBinding.inflate(layoutInflater, this)
    }

    private val searchContentAdapter by lazy {
        SearchContentAdapter()
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        binder.rvSearchContent.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = searchContentAdapter
        }

    }

    fun setSearchContent(lstOfSuggestion: List<WeatherCity>) {
        searchContentAdapter.submitList(lstOfSuggestion)
    }


    private class SearchContentAdapter : ListAdapter<WeatherCity, RecyclerView.ViewHolder>(SearchSuggestionDiffCallback()) {
        private var contentTapped: ((id: WeatherCity) -> Unit)? =null


        fun contentTapped(action: (id: WeatherCity) -> Unit) {
            this.contentTapped = action
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val binder = ListItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return SearchSuggestionViewHolder(binder)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as SearchSuggestionViewHolder).bind(getItem(position))
        }

        inner class SearchSuggestionViewHolder(
            private var binder: ListItemSearchBinding
        ) : RecyclerView.ViewHolder(binder.root) {
            private lateinit var item: WeatherCity

            init {

                binder.root.setOnClickListener {
                    contentTapped?.invoke(item)
                }
            }


            fun bind(item: WeatherCity) {
                this.item = item

                binder.tvCityName.text = item.name
                binder.tvCountryName.text = item.country
            }
        }


    }



    private class SearchSuggestionDiffCallback : DiffUtil.ItemCallback<WeatherCity>() {

        override fun areItemsTheSame(oldItem: WeatherCity, newItem: WeatherCity): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: WeatherCity, newItem: WeatherCity): Boolean {
            return oldItem == newItem
        }

    }

    fun searchContentClicked(action: (id: WeatherCity) -> Unit) {
        searchContentAdapter.contentTapped(action)
    }




  // data class CityModel(var name:String,var lat:String,var lon:String)

}