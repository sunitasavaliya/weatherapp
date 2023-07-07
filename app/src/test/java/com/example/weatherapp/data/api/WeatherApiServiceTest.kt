package com.example.weatherapp.data.api


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class WeatherApiServiceTest {
    private lateinit var service: WeatherApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))//We will use MockWebServers url
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun getWeatherByCityName_sentRequest_receivedExpected() {
        runBlocking {
            // Prepare fake response
            enqueueMockResponse("cityresponse.json")
            //Send Request to the MockServer
            val responseBody = service.getWeatherByCityName("chicago").body()
            //Request received by the mock server
            val request = server.takeRequest()

            assertTrue(responseBody != null)
            assertTrue(request.path == ("/data/2.5/weather?q=chicago&appid=3ccb48f76dc33bc6bc712e70f75cc6ff"))
        }
    }

    @Test
    fun getWeatherByCityName_receivedResponse_correctLanANdLon() {
        runBlocking {
            // Prepare fake response
            enqueueMockResponse("cityresponse.json")
            //Send Request to the MockServer
            val responseBody = service.getWeatherByCityName("chicago").body()
            //Request received by the mock server
            val request = server.takeRequest()

            assertTrue(responseBody?.coord?.lat == 41.85 && responseBody?.coord?.lon == -87.65)
        }
    }

    @Test
    fun getWeatherByLatLon_sentRequest_receivedExpected() {
        runBlocking {
            // Prepare fake response
            enqueueMockResponse("cityresponse.json")
            //Send Request to the MockServer
            val responseBody = service.getWeatherByLatLon(41.85, -87.65).body()
            //Request received by the mock server
            val request = server.takeRequest()

            assertTrue(responseBody != null)
            assertTrue(request.path == ("/data/2.5/weather?lat=41.85&lon=-87.65&appid=3ccb48f76dc33bc6bc712e70f75cc6ff"))
        }
    }

    @Test
    fun getWeatherByLatLon_receivedResponse_correctCityName() {
        runBlocking {
            // Prepare fake response
            enqueueMockResponse("cityresponse.json")
            //Send Request to the MockServer
            val responseBody = service.getWeatherByLatLon(41.85, -87.65).body()
            //Request received by the mock server
            val request = server.takeRequest()

            assertTrue(responseBody?.name == "Chicago")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}