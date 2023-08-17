package com.example.weatherapiapp.response

data class WeatherResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)