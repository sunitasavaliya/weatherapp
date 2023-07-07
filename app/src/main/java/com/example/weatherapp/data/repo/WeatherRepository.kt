package com.example.weatherapp.data.repo

import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.util.Resource

interface WeatherRepository {
    suspend fun getWeatherDataByCityName(cityName: String): Resource<CityApiResponse>
    suspend fun getWeatherDataByLatLon(lat: Double, lon: Double): Resource<CityApiResponse>
}