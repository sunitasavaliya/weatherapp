package com.example.weatherapp

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.data.model.Clouds
import com.example.weatherapp.data.model.Coord
import com.example.weatherapp.data.model.Main
import com.example.weatherapp.data.model.Sys
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.Wind
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class SearchWeatherFragmentTest {

    private val cityWeatherData = CityApiResponse(
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

    @Test
    fun clickCard_navigateToDetailFragment() = runTest {

        // GIVEN - On the home screen
        //launchFragmentInContainer<SearchWeatherFragment>(Bundle(), R.style.Theme_MyApplication)
        val scenario = launchFragmentInContainer<SearchWeatherFragment>()
        val navController = mock(NavController::class.java)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        // WHEN - Click on the "+" button
        onView(withId(R.id.cv_weather)).perform(click())
        verify(navController).navigate(
            SearchWeatherFragmentDirections.actionSearchWeatherFragmentToWeatherDetailFragment(
                weatherdata = cityWeatherData
            )
        )

    }
    @Test
    fun selectedCityWeather_DisplayedInUi() {
        launchFragmentInContainer<SearchWeatherFragment>(Bundle(), R.style.Theme_MyApplication)

        // THEN - Weather details are displayed on the screen
        onView(withId(R.id.tv_cityName)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    cityWeatherData.name
                )
            )
        )
        onView(withId(R.id.tv_temp)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.tv_weatherCondition)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    "Mist"
                )
            )
        )
    }
}