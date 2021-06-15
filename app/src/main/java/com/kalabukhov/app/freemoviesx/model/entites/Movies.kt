package com.kalabukhov.app.freemoviesx.model.entites

data class Movies(
    val nameMovie: Movie = getDefaultCity1(),
    val starsMovie: Movie = getDefaultCity1(),
    val feelsLike: String = "0"
)

fun getDefaultCity1() = Movie("Super Men", 8.6, "5")
