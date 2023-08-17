package com.example.weatherapiapp.data

import com.example.weatherapiapp.domain.WeatherApiRepository
import com.example.weatherapiapp.response.WeatherResponse
import com.example.weatherapiapp.retrofit.WeatherAppApi
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Call
import javax.inject.Inject


@ActivityScoped
class WeatherApiRepositoryImpl @Inject constructor(private val api: WeatherAppApi)
    : WeatherApiRepository {
    override fun getWeather(city: String, days: Int, key: String): Call<WeatherResponse> {
        return api.getWeather(city, days, key)
    }
}