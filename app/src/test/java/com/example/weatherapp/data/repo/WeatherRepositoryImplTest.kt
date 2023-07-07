package com.example.weatherapp.data.repo

import com.example.weatherapp.data.datasrc.FakeRemoteDataSrcImplTest
import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Coord
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Sys
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.Wind
import com.example.weatherapp.data.repoImpl.WeatherRepositoryImpl
import com.example.weatherapp.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {
    private lateinit var remoteDataSrcImplTest: FakeRemoteDataSrcImplTest
    private lateinit var remoteDataSrcImplTest2: FakeRemoteDataSrcImplTest

    // Class under test
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherRepository2: WeatherRepository

    private val remoteData: CityApiResponse = CityApiResponse(
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

    @Before
    fun createRepository() {
        remoteDataSrcImplTest = FakeRemoteDataSrcImplTest(remoteData)
        remoteDataSrcImplTest2 = FakeRemoteDataSrcImplTest(null)

        // Get a reference to the class under test
        weatherRepository = WeatherRepositoryImpl(
            remoteDataSrcImplTest
        )
        weatherRepository2 = WeatherRepositoryImpl(
            remoteDataSrcImplTest2
        )

    }

    @Test
    fun getWeatherDataByCityName_requestsWeatherFromRemoteDataSource() = runTest {
        // When city are requested from the Weather repository
        val response =
            weatherRepository.getWeatherDataByCityName("chicago") as Resource.Success<CityApiResponse>

        // Then weather are loaded from the remote data source
        assertThat(response.data == remoteData)
    }

    @Test
    fun getWeatherDataByCityName_requestsWeatherFromRemoteDataSource_receivedExpected() {
        runTest {
            // When city are requested from the Weather repository
            val response =
                weatherRepository2.getWeatherDataByCityName("chicago")

            // Then weather are loaded from the remote data source
            assertThat(response.data == null)
        }
    }

    @Test
    fun getWeatherDataByLatLon_requestsWeatherFromRemoteDataSource() = runTest {
        // When city are requested from the Weather repository
        val response =
            weatherRepository.getWeatherDataByLatLon(
                41.85,
                -87.65
            ) as Resource.Success<CityApiResponse>

        // Then weather are loaded from the remote data source
        assertThat(response.data == remoteData)
    }

    @Test
    fun getWeatherDataByLatLon_requestsWeatherFromRemoteDataSource_receivedExpected() = runTest {
        // When city are requested from the Weather repository

        val response =
            weatherRepository2.getWeatherDataByLatLon(41.85, 645.565)

        // Then weather are loaded from the remote data source
        assertThat(response.data == null)
    }
}