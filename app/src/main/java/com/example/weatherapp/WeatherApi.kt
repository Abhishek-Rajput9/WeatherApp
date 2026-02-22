package com.example.weatherapp

import androidx.compose.ui.input.key.Key
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
     @Query("q") city: String,
     @Query("appid") apiKey: String,
     @Query("units") units: String = "metric"
    ): WeatherResponse

    object RetrofitInstance {
        val api: WeatherApi by lazy {
            Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java) }
    }
}