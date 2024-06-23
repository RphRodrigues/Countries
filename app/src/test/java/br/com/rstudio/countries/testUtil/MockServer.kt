package br.com.rstudio.countries.testUtil

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.Retrofit

class MockServer : KoinTest {

  private lateinit var mockWebServer: MockWebServer

  fun start() {
    mockWebServer = MockWebServer()

    get<Retrofit> {
      parametersOf(mockWebServer.url("/"))
    }
  }

  fun responseSuccess(jsonFileName: String) {
    val json = readFile(jsonFileName)

    val mockResponse = MockResponse().apply {
      setResponseCode(200).setBody(json)
    }

    mockWebServer.enqueue(mockResponse)
  }

  fun responseError() {
    val mockResponse = MockResponse().apply {
      setResponseCode(400)
    }

    mockWebServer.enqueue(mockResponse)
  }

  fun stop() {
    mockWebServer.shutdown()
  }
}
