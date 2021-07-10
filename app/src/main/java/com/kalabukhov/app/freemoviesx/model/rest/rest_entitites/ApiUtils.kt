package com.kalabukhov.app.freemoviesx.model.rest.rest_entitites

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {

    val baseUrlMainPart = "https://api.themoviedb.org/"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .method(original.method(), original.body())
                .build()
            chain.proceed(request)
        }
        return httpClient.build()
    }
}