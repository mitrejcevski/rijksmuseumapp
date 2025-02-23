package nl.jovmit.rmapp.network

import com.google.common.truth.Truth.assertThat
import okhttp3.Call
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

class AppendApiKeyTest {

  @Test
  fun appendsApiKey() {
    val interceptor = AuthInterceptor()

    val result = interceptor.intercept(createDummyRequest())

    assertThat(result.request.url.queryParameter("key"))
      .isEqualTo("LI6D4NbA")
  }

  private fun createDummyRequest(): Interceptor.Chain {
    val request = Request.Builder()
      .url("https://dummy.url/")
      .build()
    return object : Interceptor.Chain {
      override fun call(): Call {
        TODO("Not yet implemented")
      }

      override fun connectTimeoutMillis(): Int {
        TODO("Not yet implemented")
      }

      override fun connection(): Connection? {
        TODO("Not yet implemented")
      }

      override fun proceed(request: Request): Response {
        return Response.Builder()
          .request(request)
          .code(200)
          .protocol(Protocol.HTTP_2)
          .message("")
          .build()
      }

      override fun readTimeoutMillis(): Int {
        TODO("Not yet implemented")
      }

      override fun request(): Request {
        return request
      }

      override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
      }

      override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
      }

      override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
        TODO("Not yet implemented")
      }

      override fun writeTimeoutMillis(): Int {
        TODO("Not yet implemented")
      }
    }
  }
}