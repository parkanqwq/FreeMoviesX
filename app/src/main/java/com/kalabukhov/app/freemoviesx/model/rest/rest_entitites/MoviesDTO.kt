package com.kalabukhov.app.freemoviesx.model.rest.rest_entitites

data class MoviesDTO (
    val id: Int?,
    val original_title: String?,
    val vote_average: Double?,
    val release_date: String?,
    val original_language: String?,
    val runtime: Int?,
    val overview: String?,
    val backdrop_path: String?
    )