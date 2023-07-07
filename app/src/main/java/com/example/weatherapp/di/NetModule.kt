package com.example.weatherapp.di

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.api.WeatherApiService
import com.example.weatherapp.data.datasrc.RemoteDataSrc
import com.example.weatherapp.data.datasrcimpl.RemoteDataSrcImpl
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.data.repoImpl.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSrc(weatherApiService: WeatherApiService): RemoteDataSrc {
        return RemoteDataSrcImpl(weatherApiService)
    }

    @Singleton
    @Provides
    fun provideRepository(remoteDataSrc: RemoteDataSrc): WeatherRepository {
        return WeatherRepositoryImpl(remoteDataSrc)
    }
}