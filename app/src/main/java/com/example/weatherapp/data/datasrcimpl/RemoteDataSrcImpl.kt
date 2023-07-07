package com.example.weatherapp.data.datasrcimpl

import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.data.datasrc.RemoteDataSrc
import com.example.weatherapp.data.model.CityApiResponse
import retrofit2.Response

class RemoteDataSrcImpl(private val api: WeatherApiService) : RemoteDataSrc {
    override suspend fun getGetWeatherDataByCityName(cityName:String): Response<CityApiResponse> {
        return api.getWeatherByCityName(cityName)
    }

    override suspend fun getGetWeatherDataByLatLon(
        lat: Double,
        lon: Double
    ): Response<CityApiResponse> {
        return api.getWeatherByLatLon(lat,lon)
    }
}