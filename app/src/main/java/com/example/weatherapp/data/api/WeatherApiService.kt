package com.example.weatherapp.data.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.model.CityApiResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("/data/2.5/weather")
    suspend fun getWeatherByCityName(@Query("q") cityName : String,@Query("appid") appId : String = BuildConfig.API_KEY) : Response<CityApiResponse>

    @GET("/data/2.5/weather")
    suspend fun getWeatherByLatLon(@Query("lat") lat : Double,@Query("lon") lon : Double,@Query("appid") appId : String = BuildConfig.API_KEY) : Response<CityApiResponse>
}