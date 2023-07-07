package com.example.weatherapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *   Here in getWeather() if have location permission On use lan lon api for very first time
 *  In getWeather() if have not location permission On try API using last saved City
*/

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val application: Application

) : AndroidViewModel(application) {
    val weather: MutableLiveData<Resource<CityApiResponse>> = MutableLiveData()
    val lat: MutableLiveData<Double> = MutableLiveData(0.0)
    val lon: MutableLiveData<Double> = MutableLiveData(0.0)
    val cityName: MutableLiveData<String> = MutableLiveData("")
    val currentWeather: MutableLiveData<CityApiResponse> = MutableLiveData()
    val isReadyToGetData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getWeather() = viewModelScope.launch {
        if (isNetworkConnected(application)) {
            try {
                withContext(Dispatchers.IO) {
                    if (lat.value != null && lat.value!! > 0.0 && lon.value != null && lat.value!! > 0.0) {
                        weather.postValue(
                            repository.getWeatherDataByLatLon(
                                lat.value!!,
                                lon.value!!
                            )
                        )
                    } else {
                        //if have location permission On use API with lat lon
                        weather.postValue(repository.getWeatherDataByCityName(cityName.value.toString()))
                    }
                }
            } catch (ex: Exception) {
                weather.postValue(Resource.Error(ex.message.toString()))
            }
        } else {
            weather.postValue(Resource.Error("No Internet"))
        }
    }

    private fun isNetworkConnected(application: Application): Boolean {

        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                    capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_ETHERNET
                    ) -> return true
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected)
                return true
        }
        return false
    }
}