package com.kalabukhov.app.freemoviesx.model.rest

import com.kalabukhov.app.freemoviesx.model.entites.PersoneMovie
import com.kalabukhov.app.freemoviesx.model.rest.rest_entitites.MoviesDTO
import com.kalabukhov.app.freemoviesx.model.rest.rest_entitites.MoviesDTOArr
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

    @GET("3/search/movie")
    fun getNextPage(
        @Query("api_key") apiKey: String,
        //@Query("language") language: String,
        @Query("page") page: Int,
        @Query("include_adult") include_adult: Boolean,
        @Query("query") query: String
    ): Call<MoviesDTOArr>

    @GET("3/person")
    fun getPerson(
        @Query("id") id: Int,
        @Query("api_key") apiKey: String
    ): Call<PersoneMovie>
}