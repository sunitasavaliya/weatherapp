package com.example.weatherapp.data.datasrc

import com.example.weatherapp.data.model.CityApiResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

class FakeRemoteDataSrcImplTest(private var response: CityApiResponse?) : RemoteDataSrc {
    override suspend fun getGetWeatherDataByCityName(cityName: String): Response<CityApiResponse> {
        response?.let {
            return Response.success(response)
        }
        return Response.error(
            400,
            ResponseBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), "")
        )
    }

    override suspend fun getGetWeatherDataByLatLon(
        lat: Double,
        lon: Double
    ): Response<CityApiResponse> {
        response?.let {
            return Response.success(response)
        }

        return Response.error(
            400,
            ResponseBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), "")
        )
    }

}