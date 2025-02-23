package nl.jovmit.rmapp.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
    val updatedUrl = request.url.newBuilder()
      .addQueryParameter("key", "LI6D4NbA")
      .build()
    val newRequest = request.newBuilder().url(updatedUrl).build()
    return chain.proceed(newRequest)
  }
}