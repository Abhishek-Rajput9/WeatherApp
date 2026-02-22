package com.example.weatherapp


class WeatherRepository {
    suspend fun getWeather(city: String , apiKey: String, ): WeatherResponse{
     return WeatherApi.RetrofitInstance.api.getWeather(city, apiKey)
    }

    }
