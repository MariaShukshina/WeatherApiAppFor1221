package com.example.weatherapiapp.retrofit

import com.example.weatherapiapp.response.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAppApi {

    @GET("forecast.json")
    fun getWeather(
        @Query("q") city: String,
        @Query("days") days: Int,
        @Query("key") key: String): Call<WeatherResponse>
}