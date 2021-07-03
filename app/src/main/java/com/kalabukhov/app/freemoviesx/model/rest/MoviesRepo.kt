package com.kalabukhov.app.freemoviesx.model.rest

import com.kalabukhov.app.freemoviesx.model.rest.rest_entitites.ApiUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepo {
    val api: MoviesAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrlMainPart)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(MoviesAPI::class.java)
    }
}

