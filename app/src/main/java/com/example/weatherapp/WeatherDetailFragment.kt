package com.example.weatherapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.databinding.FragmentWeatherDetailBinding
import com.example.weatherapp.util.GlobalVariable


class WeatherDetailFragment : Fragment() {
    private val args: WeatherDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentWeatherDetailBinding
    private lateinit var cityApiResponse: CityApiResponse

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherDetailBinding.bind(view)
        cityApiResponse = args.weatherdata
        binding.apply {
            tvCityName.text = cityApiResponse.name

            tvTemp.text = GlobalVariable.convertFahrenheitToCelsius(cityApiResponse.main.temp)
                .toString() + getString(R.string.degree)

            tvWeatherCondition.text = buildString {
                append(cityApiResponse.weather[0].description.substring(0, 1).uppercase())
                append(cityApiResponse.weather[0].description.substring(1))
            }

            tvLowTemp.text =
                "L : " + GlobalVariable.convertFahrenheitToCelsius(cityApiResponse.main.tempMin)
                    .toString() + getString(R.string.degree)

            tvHighTemp.text =
                "H : " + GlobalVariable.convertFahrenheitToCelsius(cityApiResponse.main.tempMax)
                    .toString() + getString(R.string.degree)

            tvSunriseValue.text =
                GlobalVariable.getTime(
                    cityApiResponse.sys.sunrise.toLong(),
                    cityApiResponse.timezone
                )

            tvSunsetValue.text =
                GlobalVariable.getTime(
                    cityApiResponse.sys.sunset.toLong(),
                    cityApiResponse.timezone
                )

            tvWindValue.text = cityApiResponse.wind.speed.toString() + " m/s"

            tvFeelslikeValue.text =
                GlobalVariable.convertFahrenheitToCelsius(cityApiResponse.main.feelsLike)
                    .toString() + getString(R.string.degree)

            tvHumidityValue.text = cityApiResponse.main.humidity.toString() + " %"

            tvPressureValue.text = cityApiResponse.main.pressure.toString() + " hPa"
        }

    }


}