package com.example.salessparrow

import com.example.salessparrow.api.ApiService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APITest {
    lateinit var mockWebServer: MockWebServer

    lateinit var apiService: ApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Test
    fun testGetImages() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("{}");
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getCatImages()
        mockWebServer.takeRequest()

        //check if response body is empty

        Assert.assertEquals(true, response.body()?.id.equals(null) ?: true)
    }

    @Test
    fun testGetImages_returnImages() = runTest {
        val mockResponse = MockResponse()
        val content = Helper.readFile("/com/example/salessparrow/data/mockResponse.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content);
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getCatImages()
        mockWebServer.takeRequest()

        //check if response body is empty

        Assert.assertEquals(false, response.body()?.equals(1) ?: true)
    }

    @Test
    fun testGetImages_returnError() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong");
        mockWebServer.enqueue(mockResponse)

        val response = apiService.getCatImages()
        mockWebServer.takeRequest()

        //check if response body is empty

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}