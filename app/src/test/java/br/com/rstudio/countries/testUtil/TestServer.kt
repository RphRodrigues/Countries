package br.com.rstudio.countries.testUtil

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.koin.core.parameter.parametersOf
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.Retrofit

class TestServer : KoinTest {

  private lateinit var mockWebServer: MockWebServer

  fun start(jsonFileName: String) {
    mockWebServer = MockWebServer()

    get<Retrofit> {
      parametersOf(mockWebServer.url("/"))
    }

    val json = readFile(jsonFileName)
    val mockResponse = MockResponse()
    mockResponse.setResponseCode(200).setBody(json)
    mockWebServer.enqueue(mockResponse)
  }

  fun stop() {
    mockWebServer.shutdown()
  }
}
