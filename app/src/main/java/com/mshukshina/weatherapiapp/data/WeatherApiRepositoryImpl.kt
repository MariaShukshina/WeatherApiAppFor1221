package com.mshukshina.weatherapiapp.data

import com.mshukshina.weatherapiapp.domain.WeatherApiRepository
import com.mshukshina.weatherapiapp.response.WeatherResponse
import com.mshukshina.weatherapiapp.retrofit.WeatherAppApi
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