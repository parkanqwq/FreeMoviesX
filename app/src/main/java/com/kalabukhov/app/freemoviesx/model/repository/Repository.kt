package com.kalabukhov.app.freemoviesx.model.repository

import com.kalabukhov.app.freemoviesx.model.entites.Movies

interface Repository {
    fun getMoviesFromServer(id: Int): Movies
    fun getMovieFromLocalStorage(): List<Movies>
}