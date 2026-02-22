import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import com.example.weatherapp.WeatherRepository
import com.example.weatherapp.WeatherResponse

class WeatherViewModel(
    private val repository: WeatherRepository = WeatherRepository()
): ViewModel() {

    val weatherState = mutableStateOf<WeatherResponse?>(null)
    val errorState = mutableStateOf<String?>(null)

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getWeather(city, apiKey)
                weatherState.value = response
                errorState.value = null
            } catch (e: Exception) {
                errorState.value = "Error: ${e.message}"
            }
        }
    }
}
