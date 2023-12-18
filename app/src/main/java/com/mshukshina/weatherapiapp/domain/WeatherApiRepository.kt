package com.mshukshina.weatherapiapp.domain

import com.mshukshina.weatherapiapp.response.WeatherResponse
import retrofit2.Call

interface WeatherApiRepository {
    fun getWeather(city: String, days: Int, key: String): Call<WeatherResponse>
}