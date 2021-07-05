package com.kalabukhov.app.freemoviesx.model.rest

import com.kalabukhov.app.freemoviesx.model.rest.rest_entitites.MoviesDTO
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MoviesAPI {
    @GET("3/movie/{id}")
    fun getMoviesAsync(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ) : Call<MoviesDTO>
}