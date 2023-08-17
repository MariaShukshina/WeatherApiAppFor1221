package com.example.weatherapiapp.ui

import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.weatherapiapp.R
import com.example.weatherapiapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    private var searchQuery = ""

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeWeatherData(binding)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = newText.toString()
                return true
            }
        })

        binding.searchButton.setOnClickListener {
            if(viewModel.internetConnectionState.value == true) {
                viewModel.getWeather(searchQuery, 5, this.getString(R.string.api_key))
            } else {
                Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeWeatherData(binding: ActivityMainBinding) {
        viewModel.weatherResponse.observe(this) {
            it?.let {
                val iconUrl = if(!it.current.condition.icon.startsWith("https:")) {
                    "https:" + it.current.condition.icon
                } else {
                    it.current.condition.icon
                }
                binding.locationTv.text = it.location.name
                Glide.with(this).load(iconUrl).into(binding.weatherIcon)
                binding.avgTempTv.text = "Average temperature: ${it.forecast.forecastday
                    .first().day.avgtemp_c}C"
                binding.maxWindTv.text = "Max wind: ${it.forecast.forecastday.first()
                    .day.maxwind_kph} kph"
                binding.conditionTextTv.text = "Condition: ${it.current.condition.text}"
                binding.avgHumidityTv.text = "Average humidity: ${it.forecast.forecastday
                    .first().day.avghumidity}"
            }
            if (viewModel.isDataRequested && it == null) {
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}