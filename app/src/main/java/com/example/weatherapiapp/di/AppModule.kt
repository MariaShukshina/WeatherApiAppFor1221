package com.example.weatherapiapp.di

import android.content.Context
import com.example.weatherapiapp.data.WeatherApiRepositoryImpl
import com.example.weatherapiapp.domain.WeatherApiRepository
import com.example.weatherapiapp.retrofit.WeatherAppApi
import com.example.weatherapiapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherApiRepository(
        api: WeatherAppApi
    ): WeatherApiRepository = WeatherApiRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideWeatherApi(@ApplicationContext context: Context): WeatherAppApi {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .cache(Cache(context.cacheDir, 10 * 1024 * 1024))
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(WeatherAppApi::class.java)
    }
}