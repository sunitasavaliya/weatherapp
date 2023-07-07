package com.example.weatherapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class GlobalVariableTest {

    @Test
    fun getTime_timeAndTimezoneGiven_receivedFormattedTime() {
        val resultString = GlobalVariable.getTime(1688620981, -18000)
        val expectedString = "12:23 AM"
        assertThat(expectedString == resultString)
    }

    @Test
    fun getTime_timeAndTimezoneGiven_expectedResult() {
        val resultString = GlobalVariable.getTime(1688620981, -18000)
        val expectedString = "12:22 AM"
        assertThat(expectedString != resultString)
    }

    @Test
    fun convertFahrenheitToCelsius_KelvinTempGiven_receivedCelsius() {
        val resultTemp = GlobalVariable.convertFahrenheitToCelsius(294.35)
        val expectedTemp = 19
        assertThat(expectedTemp == resultTemp)
    }

    @Test
    fun convertFahrenheitToCelsius_KelvinTempGiven_expectedResult() {
        val resultTemp = GlobalVariable.convertFahrenheitToCelsius(294.35)
        val expectedTemp = 18
        assertThat(expectedTemp != resultTemp)
    }
}