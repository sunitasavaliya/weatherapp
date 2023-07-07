package com.example.weatherapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Coord
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Sys
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.Wind
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@RunWith(AndroidJUnit4::class)
class WeatherDetailFragmentTest {
    @Test
    fun selectedCityWeather_DisplayedInUi() {
        val cityWeatherData = CityApiResponse(
            coord = Coord(-87.65, 41.85),
            weather = listOf(
                Weather(
                    id = 701,
                    main = "Mist",
                    description = "mist",
                    icon = "50n"
                )
            ),
            base = "stations",
            main = Main(
                temp = 294.35,
                feelsLike = 294.98,
                tempMin = 293.35,
                tempMax = 295.19,
                pressure = 1013,
                humidity = 94
            ),
            visibility = 8047,
            wind = Wind(
                speed = 3.09,
                deg = 230
            ),
            clouds = Clouds(
                all = 100
            ),
            dt = 1688620981,
            sys = Sys(
                type = 2,
                id = 2075214,
                country = "US",
                sunrise = 1688638914,
                sunset = 1688693302
            ),
            timezone = -18000,
            id = 4887398,
            name = "Chicago",
            cod = 200
        )

        val bundle = WeatherDetailFragmentArgs(cityWeatherData).toBundle()
        launchFragmentInContainer<WeatherDetailFragment>(bundle, R.style.Theme_MyApplication)

        // THEN - Weather details are displayed on the screen
        onView(withId(R.id.tv_cityName)).check(matches(withText(cityWeatherData.name)))
        onView(withId(R.id.tv_temp)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_weatherCondition)).check(matches(withText("Mist")))
        onView(withId(R.id.tv_highTemp)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_lowTemp)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_sunriseValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_sunsetValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_windValue)).check(matches(withText(cityWeatherData.wind.speed.toString() + " m/s")))
        onView(withId(R.id.tv_feelslikeValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_humidityValue)).check(matches(withText(cityWeatherData.main.humidity.toString() + " %")))
        onView(withId(R.id.tv_pressureValue)).check(matches(withText(cityWeatherData.main.pressure.toString() + " hPa")))
    }


}