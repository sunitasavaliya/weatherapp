package com.example.weatherapp.data.repo

import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Coord
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Sys
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.Wind
import com.example.weatherapp.util.Resource

class FakeWeatherRepositoryTest : WeatherRepository {

    private var weather = CityApiResponse(
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

    override suspend fun getWeatherDataByCityName(cityName: String): Resource<CityApiResponse> {

        return Resource.Success(weather)

    }

    override suspend fun getWeatherDataByLatLon(
        lat: Double,
        lon: Double
    ): Resource<CityApiResponse> {
        return Resource.Success(weather)
    }
}