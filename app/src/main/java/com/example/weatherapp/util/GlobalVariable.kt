package com.example.weatherapp.util

import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class GlobalVariable {
    companion object {
        private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a")

        //User Enter First Time
        //var isUserEnterFirstTime = false

        fun getTime(time: Long, timezone: Int): String {
            return ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(time * 1000L),
                ZoneOffset.ofTotalSeconds(timezone)
            ).format(timeFormatter)
        }

        fun convertFahrenheitToCelsius(fahrenheit: Double): Int {
            return (fahrenheit - 273.15).toInt()
        }
    }
}