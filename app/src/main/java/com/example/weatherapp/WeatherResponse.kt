package com.example.weatherapp

data class WeatherResponse(
    val name: String,
    val wind: Wind,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Double,
    val coord: Coordinates
)
data class Main(
    val temp: Double,
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int
)

data class Weather(
    val description: String,
)
data class Wind(
    val speed: Double,
)
data class Coordinates(
    val lon: Double,
    val lat: Double
)