package com.jpmorgan.networks.weather.core

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.moczul.ok2curl.CurlInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import com.jpmorgan.networks.weather.gsonzipper.`object`.ZippedObjectTypeAdapterFactory
import com.jpmorgan.networks.weather.gsonzipper.list.ZippedListTypeAdapterFactory
import com.jpmorgan.networks.BuildConfig
import com.jpmorgan.networks.weather.WeatherUrl
import com.jpmorgan.networks.weather.source.remote.WeatherClient

internal class ServiceGenerator {

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().also { interceptor ->
                        interceptor.level = HttpLoggingInterceptor.Level.BODY
                    })

                    addInterceptor(CurlInterceptor { msg -> Timber.v(msg) })
                }

            }.build()
    }


    private val gsonConverterFactory by lazy {
        GsonConverterFactory.create(
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapterFactory(ZippedListTypeAdapterFactory())
                .registerTypeAdapterFactory(ZippedObjectTypeAdapterFactory())
                .setLenient()
                .create()
        )
    }


    fun getWeatherClient(): WeatherClient {

        val retrofit = Retrofit.Builder()
            .baseUrl(WeatherUrl.BASE)
            .client(httpClient.newBuilder().build())
            .addConverterFactory(gsonConverterFactory)
            .build()

        return retrofit.create(WeatherClient::class.java)
    }


}
