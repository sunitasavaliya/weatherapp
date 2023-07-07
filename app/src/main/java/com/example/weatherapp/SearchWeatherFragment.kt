package com.example.weatherapp

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.weatherapp.data.model.CityApiResponse
import com.example.weatherapp.databinding.FragmentSearchWeatherBinding
import com.example.weatherapp.util.GlobalVariable
import com.example.weatherapp.util.GlobalVariable.Companion.convertFahrenheitToCelsius
import com.example.weatherapp.util.Resource
import com.example.weatherapp.viewmodel.WeatherViewModel


class SearchWeatherFragment : Fragment() {

    private lateinit var binding: FragmentSearchWeatherBinding
    private lateinit var viewModel: WeatherViewModel
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchWeatherBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel

        sharedPref = (activity as MainActivity).sharedPref

        binding.etCity.addTextChangedListener(textWatcher)

        viewModel.weather.postValue(Resource.Loading())

        binding.btSearch.setOnClickListener(View.OnClickListener {
            if (binding.etCity.text.toString() == "") {
                Toast.makeText(activity, "Fill the City Name ", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.cityName.value = binding.etCity.text.toString()
                viewModel.lat.value = 0.0
                viewModel.lon.value = 0.0
                viewModel.isReadyToGetData.value = true
            }
        })

        binding.cvWeather.setOnClickListener(View.OnClickListener {
            val action = viewModel.currentWeather.value?.let { it1 ->
                SearchWeatherFragmentDirections.actionSearchWeatherFragmentToWeatherDetailFragment(
                    it1
                )
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        })

        viewModel.isReadyToGetData.observe(viewLifecycleOwner, Observer { flag ->
            if (flag) {
                viewModel.getWeather()
            } else {
                showNoDataView()
            }
        })

        viewModel.weather.observe(viewLifecycleOwner, Observer { res ->
            hideSoftKeyboard()
            when (res) {
                is Resource.Success -> {
                    if (res.data != null) {
                        viewModel.currentWeather.value = res.data
                        sharedPref.edit()?.putString(getString(R.string.saved_city), res.data.name)
                            ?.commit()
                        setUi(res.data)
                    } else {
                        showNoDataView()
                    }
                }

                is Resource.Error -> {
                    showNoDataView()
                    res.message?.let {
                        Toast.makeText(activity, "City not found", Toast.LENGTH_SHORT).show()
                    }
                }

                is Resource.Loading -> {
                    showLoadingView()
                }
            }
        })
    }

    private fun showNoDataView() {
        binding.loadingView.visibility = View.GONE
        binding.dataView.visibility = View.GONE
        binding.noDataView.visibility = View.VISIBLE
    }

    private fun showLoadingView() {
        binding.loadingView.visibility = View.VISIBLE
        binding.dataView.visibility = View.GONE
        binding.noDataView.visibility = View.GONE
    }

    private fun showDataView() {
        binding.loadingView.visibility = View.GONE
        binding.dataView.visibility = View.VISIBLE
        binding.noDataView.visibility = View.GONE
    }

    private fun setUi(data: CityApiResponse) {
        showDataView()

        binding.tvCityName.text = data.name.trim()

        val weatherCondition = data.weather[0].description.trim()

        binding.tvTime.text = GlobalVariable.getTime(data.dt.toLong(), data.timezone)

        binding.tvWeatherCondition.text = buildString {
            append(weatherCondition.substring(0, 1).uppercase())
            append(weatherCondition.substring(1))
        }

        val circularProgressDrawable = CircularProgressDrawable(activity as MainActivity)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(activity as MainActivity)
            .load(BuildConfig.ICON_URL + data.weather.get(0).icon.trim() + ".png")
            .placeholder(circularProgressDrawable)
            //here used glide automatic cache mechanism for cache image
            .diskCacheStrategy(
                DiskCacheStrategy.AUTOMATIC
            ).into(binding.ivIcon)

        binding.tvTemp.text =
            convertFahrenheitToCelsius(data.main.temp).toString() + getString(R.string.degree)

        binding.tvHighTemp.text = "H : " +
                convertFahrenheitToCelsius(data.main.tempMax).toString() + getString(
            R.string.degree
        )

        binding.tvLowTemp.text = "L : " +
                convertFahrenheitToCelsius(data.main.tempMin).toString() + getString(
            R.string.degree
        )
    }


    private fun hideSoftKeyboard() {
        try {
            val inputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                requireActivity().currentFocus!!.windowToken,
                0
            )
        } catch (e: Exception) {
            Log.e("Keyboard is not open", e.message.toString())
        }
    }


    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            //after text changed
        }

        override fun beforeTextChanged(
            s: CharSequence, start: Int, count: Int,
            after: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {
            if (s.toString().trim() == "") {
                if (viewModel.currentWeather.value != null) {
                    showDataView()
                } else {
                    showNoDataView()
                }
            }
        }
    }

}