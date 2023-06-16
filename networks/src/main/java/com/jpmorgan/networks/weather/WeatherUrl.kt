package com.jpmorgan.networks.weather

object WeatherUrl {

    const val BASE = "http://api.openweathermap.org/"

    const val WEATHER_DETAILS="data/2.5/weather"
    const val CITY_GEOCODE="geo/1.0/direct"
    const val THUMBNAIL = "https://api.openweathermap.org/"

    object Agent {

        const val KEY_AGENT ="user-agent"
        const val VALUE_AGENT ="Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; ADR6400L 4G Build/FRG83D) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1"

    }
}

