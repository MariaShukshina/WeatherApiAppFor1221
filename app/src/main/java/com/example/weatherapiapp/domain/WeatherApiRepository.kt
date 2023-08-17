package com.example.weatherapiapp.domain

import com.example.weatherapiapp.response.WeatherResponse
import retrofit2.Call

interface WeatherApiRepository {
    fun getWeather(city: String, days: Int, key: String): Call<WeatherResponse>
}