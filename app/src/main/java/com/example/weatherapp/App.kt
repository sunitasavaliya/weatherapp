package com.example.weatherapp

import android.app.Application
import android.util.Log
import com.example.weatherapp.util.GlobalVariable
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App :Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalVariable.isUserEnterFirstTime  = true
    }
}