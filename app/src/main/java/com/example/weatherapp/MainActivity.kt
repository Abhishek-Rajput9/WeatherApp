package com.example.weatherapp

import WeatherViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.WeatherRepository

class MainActivity : ComponentActivity() {
    private val apiKey = "384e9e6454af68d2a27d4d065a3b9a59" // Replace with your OpenWeatherMap key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp(apiKey)
        }
    }
}



@Composable
fun WeatherApp(apiKey: String) {
    val viewModel: WeatherViewModel = viewModel()
    var city by remember { mutableStateOf("Paris") } // Default to Paris

    // Fetch weather on first launch
    LaunchedEffect(Unit) {
        viewModel.fetchWeather(city, apiKey)
    }

    val weather = viewModel.weatherState.value
    val error = viewModel.errorState.value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF131313) // Pure black background like the image
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Search Bar (Rounded)
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                placeholder = { Text("Location", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1C1E),
                    unfocusedContainerColor = Color(0xFF1C1C1E),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.fetchWeather(city, apiKey) })
            )

            Spacer(modifier = Modifier.height(60.dp))

            if (weather != null) {
                // 2. City Name
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )


                Spacer(modifier = Modifier.height(40.dp))

                // 3. Large Temperature
                Text(
                    text = "${weather.main.temp.toInt()}°",
                    style = TextStyle(
                        fontSize = 100.sp,
                        fontWeight = FontWeight.W200,
                        color = Color.White
                    )
                )

                // 4. Condition Text
                Text(
                    text = weather.weather[0].description.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Gray
                )
                Spacer( modifier = Modifier.height(50.dp))

                // 5. Info Cards (Humidity & Condition)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    WeatherDetailCard("Wind" , "${weather.wind.speed}", Modifier.weight(1f))
                    WeatherDetailCard("Humidity", "${weather.main.humidity}%", Modifier.weight(1f))
                }
                Spacer( modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    WeatherDetailCard("Latitude" , "${weather.coord.lat}", Modifier.weight(1f))
                    WeatherDetailCard("Longitude", "${weather.coord.lon}", Modifier.weight(1f))
                }
                Spacer( modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    WeatherDetailCard("Sea Level" , "${weather.main.sea_level}", Modifier.weight(1f))
                    WeatherDetailCard("Ground Level", "${weather.main.grnd_level}", Modifier.weight(1f))
                }
            }

            if (error != null) {
                Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 20.dp))
            }
        }
    }
}

@Composable
fun WeatherDetailCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1C1C1E)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = label, color = Color.Gray, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, color = Color.White, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}