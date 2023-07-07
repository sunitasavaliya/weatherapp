package com.example.weatherapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapp.data.model.*
import com.example.weatherapp.data.repo.FakeWeatherRepositoryTest
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Use a fake repository to be injected into the viewmodel
    private lateinit var weatherRepositoryTest: FakeWeatherRepositoryTest

    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setupViewModel() {
        // We initialise the tasks to 3, with one active and two completed
        weatherRepositoryTest = FakeWeatherRepositoryTest()
        weatherViewModel =
            WeatherViewModel(weatherRepositoryTest, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun getWeather_receivedEvent() = runTest {
        // When adding data
        val cityWeatherData = CityApiResponse(
            coord = Coord(-87.65, 41.85),
            weather = listOf(
                Weather(
                    id = 701,
                    main = "Mist",
                    description = "mist",
                    icon = "50n"
                )
            ),
            base = "stations",
            main = Main(
                temp = 294.35,
                feelsLike = 294.98,
                tempMin = 293.35,
                tempMax = 295.19,
                pressure = 1013,
                humidity = 94
            ),
            visibility = 8047,
            wind = Wind(
                speed = 3.09,
                deg = 230
            ),
            clouds = Clouds(
                all = 100
            ),
            dt = 1688620981,
            sys = Sys(
                type = 2,
                id = 2075214,
                country = "US",
                sunrise = 1688638914,
                sunset = 1688693302
            ),
            timezone = -18000,
            id = 4887398,
            name = "Chicago",
            cod = 200
        )
        var resource = Resource.Success(cityWeatherData)
        weatherViewModel.getWeather()
        advanceUntilIdle()
        assertTrue(resource.data?.name == weatherViewModel.weather?.value?.data?.name)
    }
}