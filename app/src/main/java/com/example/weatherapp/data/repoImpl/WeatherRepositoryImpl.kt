package com.example.weatherapp.data.repoImpl

import android.util.Log
import com.example.weatherapp.data.datasrc.RemoteDataSrc
import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.util.Resource
import retrofit2.Response

class WeatherRepositoryImpl (private val remoteDataSrc: RemoteDataSrc) : WeatherRepository {
    override suspend fun getWeatherDataByCityName(cityName: String): Resource<CityApiResponse> {
        return responseToResource(remoteDataSrc.getGetWeatherDataByCityName(cityName));
    }

    override suspend fun getWeatherDataByLatLon(
        lat: Double,
        lon: Double
    ): Resource<CityApiResponse> {
        return responseToResource(remoteDataSrc.getGetWeatherDataByLatLon(lat,lon));
    }

    private fun responseToResource(response: Response<CityApiResponse>): Resource<CityApiResponse> {
        if(response.isSuccessful){
            response?.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}