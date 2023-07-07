package com.example.weatherapp.di

import android.app.Application
import com.example.weatherapp.data.repo.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun getWeatherViewModelFactory(repository: WeatherRepository,application: Application):WeatherViewModelFactory{
        return WeatherViewModelFactory(repository,application)
    }
}