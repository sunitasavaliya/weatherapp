package com.example.weatherapp.data.datasrc

import com.example.weatherapp.data.model.CityApiResponse
import retrofit2.Response

interface RemoteDataSrc {
    suspend fun getGetWeatherDataByCityName(cityName: String):Response<CityApiResponse>
    suspend fun getGetWeatherDataByLatLon(lat : Double,lon:Double):Response<CityApiResponse>
}