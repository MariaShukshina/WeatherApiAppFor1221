package com.example.weatherapiapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.weatherapiapp.domain.WeatherApiRepository
import com.example.weatherapiapp.response.WeatherResponse
import com.example.weatherapiapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val repository: WeatherApiRepository
): ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherResponse?>(null)
    val weatherResponse
        get() = _weatherResponse

    private val _internetConnectionState = MutableLiveData(false)
    val internetConnectionState
        get() = _internetConnectionState

    private var _isDataRequested = false
    val isDataRequested
        get() = _isDataRequested


    init {
        viewModelScope.launch {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkCallback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    _internetConnectionState.postValue(true)
                    getWeather("moscow",
                        5, Constants(context).key)
                }

                override fun onLost(network: Network) {
                    _internetConnectionState.postValue(false)
                }
            }
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }
    }

    fun getWeather(city: String, days: Int, key: String) {
        viewModelScope.launch {
            val call: Call<WeatherResponse> = repository.getWeather(city, days, key)
            call.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    if (response.body() == null) {
                        _weatherResponse.postValue(null)
                        return
                    }
                    _weatherResponse.postValue(response.body())
                    _isDataRequested = true
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}